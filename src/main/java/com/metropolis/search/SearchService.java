package com.metropolis.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.TermsQuery;
import org.apache.lucene.search.*;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.metropolis.mvc.model.Shop;
import com.metropolis.repository.ShopRepository;
import com.metropolis.search.SearchMatcher.Score;

@Service
public class SearchService implements InitializingBean {
	@Autowired
	private ShopRepository shopRepo;
	@Autowired
	private SearchMatcher matcher;
	@Autowired
	private Directory indexDirectory;
	private IndexSearcher searcher;

	@Override
	public void afterPropertiesSet() throws Exception {
		searcher = new IndexSearcher(DirectoryReader.open(indexDirectory));
	}

	public List<SearchResult<Shop>> search(SearchRequest request) throws IOException {
		Map<Integer, Score> filter = null;
		if (request.hasConstraints()) {
			filter = matcher.match(request, request.getSort());
			if (filter.isEmpty())
				return Collections.emptyList();
		}

		List<SearchResult<Shop>> result;
		int start = request.getStart();
		int end = start + request.getCount();
		int normFactor = 0;
		if (filter != null) {
			if (request.getCategory() != null)
				++normFactor;
			if (request.getTags() != null && !request.getTags().isEmpty())
				++normFactor;
			if (request.getAttributes() != null && !request.getAttributes().isEmpty())
				++normFactor;
		}
		if (request.getQuery() != null) {
			boolean requiresRescoring = filter != null;
			BooleanQuery.Builder builder = new BooleanQuery.Builder();
			if (filter != null)
				builder.add(new TermsQuery("id", convertToBytes(new ArrayList<>(filter.keySet()))), Occur.MUST);
			builder.add(new TermQuery(new Term("name", request.getQuery())), Occur.SHOULD);
			builder.add(new TermQuery(new Term("sd", request.getQuery())), Occur.SHOULD);
			builder.add(new TermQuery(new Term("d", request.getQuery())), Occur.SHOULD);
			builder.add(new TermQuery(new Term("c", request.getQuery())), Occur.SHOULD);
			builder.add(new TermQuery(new Term("t", request.getQuery())), Occur.SHOULD);
			builder.add(new TermQuery(new Term("a", request.getQuery())), Occur.SHOULD);
			TopDocs docs;
			if (!requiresRescoring)
				docs = searcher.search(builder.build(), 100000, createLuceneSort(request));
			else {
				docs = searcher.search(builder.build(), 100000);
			}
			result = new ArrayList<>(docs.scoreDocs.length);
			List<Integer> shopsToFetch = new ArrayList<>();
			for (int i = 0; i < docs.scoreDocs.length; ++i) {
				ScoreDoc pointer = docs.scoreDocs[i];
				Document doc = searcher.doc(pointer.doc);
				SearchResult<Shop> singleResult = new SearchResult<>();
				singleResult.setId(doc.getField("id").numericValue().intValue());
				if (requiresRescoring) {
					singleResult.setScore((pointer.score + filter.get(singleResult.getId()).total / normFactor) / 2);
				} else {
					singleResult.setScore(pointer.score);
				}
				singleResult.setType("shop");
				if (filter == null) {
					singleResult.getMeta().put("name", doc.get("name"));
				} else {
					singleResult.getMeta().put("name", filter.get(singleResult.getId()).name);
				}
				if (!requiresRescoring && start <= i && i < end)
					shopsToFetch.add(singleResult.getId());
				result.add(singleResult);
			}
			if (requiresRescoring) {
				Collections.sort(result, new SearchResultComparator(request.getSort()));
				for (int i = start; i < end && i < result.size(); ++i)
					shopsToFetch.add(result.get(i).getId());
			}
			Map<Integer, Shop> fetched = shopRepo.findAll(shopsToFetch).stream().collect(Collectors.toMap(Shop::getId, Function.identity()));
			for (int i = start; i < end && i < result.size(); ++i) {
				SearchResult<Shop> singleResult = result.get(i);
				singleResult.setData(fetched.get(singleResult.getId()));
			}
		} else {
			if (filter != null) {
				List<Integer> shopsToFetch = new ArrayList<>();
				result = new ArrayList<>(filter.size());
				for (Entry<Integer, Score> e: filter.entrySet()) {
					SearchResult<Shop> singleResult = new SearchResult<>();
					singleResult.setId(e.getKey());
					singleResult.setScore(e.getValue().total / normFactor);
					singleResult.setType("shop");
					singleResult.getMeta().put("name", e.getValue().name);
					result.add(singleResult);
				}
				Collections.sort(result, new SearchResultComparator(request.getSort()));
				for (int i = start; i < end && i < result.size(); ++i)
					shopsToFetch.add(result.get(i).getId());
				Map<Integer, Shop> fetched = shopRepo.findAll(shopsToFetch).stream().collect(Collectors.toMap(Shop::getId, Function.identity()));
				for (int i = start; i < end && i < result.size(); ++i) {
					SearchResult<Shop> singleResult = result.get(i);
					singleResult.setData(fetched.get(singleResult.getId()));
				}
			} else {
				// TODO sort and pageable
				result = null;
			}
		}
		return result;
	}

	private static Sort createLuceneSort(SearchRequest request) {
		if (request.getSort() == null)
			return null;
		List<SortField> sortFields = new ArrayList<>();
		for (Order o: request.getSort()) {
			String property = o.getProperty();
			if ("score".equalsIgnoreCase(property) || "relevance".equalsIgnoreCase(property)) {
				sortFields.add(new SortField(null, SortField.Type.SCORE, o.getDirection() == Direction.DESC));
			} else {
				sortFields.add(new SortField(property, SortField.Type.STRING_VAL, o.getDirection() == Direction.DESC));
			}
		}
		return sortFields.isEmpty() ? null : new Sort(sortFields.toArray(new SortField[sortFields.size()]));
	}

	private static BytesRef[] convertToBytes(List<Integer> ints) {
		byte[] rawData = new byte[ints.size() * 6];
		BytesRef[] result = new BytesRef[ints.size()];
		for (int i = 0, offset = 0; i < ints.size(); ++i, offset += 6) {
			int val = ints.get(i);
			result[i] = new BytesRef(rawData, offset, 6);
			int nChars = 5;
			rawData[offset] = 0x60/*NumericUtils.SHIFT_START_INT*/;
			int sortableBits = val ^ 0x80000000;
			while (nChars > 0) {
				rawData[offset + nChars--] = (byte) (sortableBits & 0x7f);
				sortableBits >>>= 7;
			}
		}
		return result;
	}

	private static class SearchResultComparator implements Comparator<SearchResult<Shop>> {
		private final org.springframework.data.domain.Sort sort;
		public SearchResultComparator(org.springframework.data.domain.Sort sort) {
			this.sort = sort;
		}
		@Override
		public int compare(SearchResult<Shop> o1, SearchResult<Shop> o2) {
			if (sort == null)
				return 0;
			for (Order o: sort) {
				int c = 0;
				if ("name".equals(o.getProperty()))
					c = compareNames(o1, o2, o.isAscending());
				else if ("score".equals(o.getProperty()) || "relevance".equals(o.getProperty())) {
					c = (int) Math.signum(o1.getScore() - o2.getScore());
					if (!o.isAscending())
						c = -c;
				}
				if (c != 0)
					return c;
			}
			return 0;
		}
		private int compareNames(SearchResult<Shop> o1, SearchResult<Shop> o2, boolean asc) {
			String name1 = (String) o1.getMeta().get("name");
			String name2 = (String) o2.getMeta().get("name");
			int c = name1.compareTo(name2);
			return asc ? c : -c;
		}
	}
}
