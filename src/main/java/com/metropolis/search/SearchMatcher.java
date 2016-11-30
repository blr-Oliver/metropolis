package com.metropolis.search;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.metropolis.repository.CategoryRepository;
import com.metropolis.repository.ShopRepository;

@Component
public class SearchMatcher {
	private static final List<Integer> NOOP_LIST = Collections.singletonList(-1);

	@Autowired
	private ShopRepository shopRepo;
	@Autowired
	private CategoryRepository categoryRepo;

	/**
	 * Searches matches for request using {@link ShopRepository#findAndScore(...)}
	 *
	 * @param request
	 *            search request
	 * @return scores for each matched shop, keyed by shop id
	 */
	public Map<Integer, Score> match(SearchRequest request, Sort sort) {
		int category = request.getCategory() == null ? 0 : request.getCategory();
		List<Integer> categories = category == 0 ? NOOP_LIST : categoryRepo.findDescendants(category, false);
		if (categories.isEmpty())
			categories = NOOP_LIST;
		Collection<Integer> tags = request.getTags() == null || request.getTags().isEmpty() ? NOOP_LIST : request.getTags();
		Collection<Integer> noChoiceA = NOOP_LIST;
		Collection<Integer> choiceA = NOOP_LIST;
		Collection<Integer> choices = NOOP_LIST;
		String token = "0";
		int aSize = 1;
		if (request.getAttributes() != null && !request.getAttributes().isEmpty()) {
			Map<Integer, Set<Integer>> attributes = request.getAttributes();
			Map<Boolean, Set<Integer>> choiceSplit = attributes.entrySet().stream()
					.collect(Collectors.partitioningBy(e -> e.getValue() != null && !e.getValue().isEmpty(), Collectors.mapping(Entry::getKey, Collectors.toSet())));
			noChoiceA = choiceSplit.get(false);
			choiceA = choiceSplit.get(true);
			if (noChoiceA == null || noChoiceA.isEmpty())
				noChoiceA = NOOP_LIST;
			if (choiceA == null || choiceA.isEmpty())
				choiceA = NOOP_LIST;
			choices = attributes.values().stream().filter(Objects::nonNull).flatMap(Set::stream).collect(Collectors.toSet());
			if (choices.isEmpty())
				choices = NOOP_LIST;
			token = createToken(attributes);
			aSize = attributes.size();
		}
		return shopRepo.findAndScore(category, categories, tags, tags.size(), noChoiceA, noChoiceA.size(), choiceA, choices, token, aSize, sort).stream()
				.collect(Collectors.toMap(r -> (Integer) r[0], Score::new));
	}

	private static String createToken(Map<Integer, Set<Integer>> attributes) {
		Map<Integer, Integer> choiceCounts = attributes.entrySet().stream().filter(e -> e.getValue() != null && !e.getValue().isEmpty())
				.collect(Collectors.toMap(e -> e.getKey() / 10, e -> e.getValue().size()));
		if (choiceCounts.isEmpty())
			return "0";
		StringBuilder sb = new StringBuilder();
		for (int i = 1; !choiceCounts.isEmpty(); ++i) {
			Integer count = choiceCounts.remove(i);
			sb.append(count == null ? 0 : count);
		}
		return sb.toString();
	}

	public static class Score {
		public final String name;
		public final Float categoryScore;
		public final Float tagScore;
		public final Float attributeScore;
		public final Float total;

		Score(Object[] row) {
			name = (String) row[1];
			categoryScore = row[2] == null ? null : ((Number) row[2]).floatValue();
			tagScore = row[3] == null ? null : ((Number) row[3]).floatValue();
			attributeScore = row[4] == null ? null : ((Number) row[4]).floatValue();
			total = ((Number) row[5]).floatValue();
		}
	}
}
