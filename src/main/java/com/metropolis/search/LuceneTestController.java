package com.metropolis.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metropolis.mvc.model.AttributeValue;
import com.metropolis.mvc.model.IAttribute;
import com.metropolis.mvc.model.Shop;
import com.metropolis.mvc.model.Tag;
import com.metropolis.repository.ShopRepository;
import com.metropolis.util.MultiMap;

@Controller
@RequestMapping("/lucene")
public class LuceneTestController {

	@Autowired
	private Directory indexDirectory;

	@Autowired
	private ShopRepository shopRepo;

	@RequestMapping("/index")
	public @ResponseBody String addTagsToIndex() throws IOException {
		IndexWriter writer = indexWriter(indexDirectory);
		for (Shop shop: shopRepo.findAll())
			writer.addDocument(createDocument(shop));
		writer.commit();
		writer.close();
		return "ok";
	}

	private static final Map<String, Field> fields;
	static {
		fields = new HashMap<>();
		fields.put("id", new IntField("id", 0, Store.YES));
		fields.put("name", new TextField("name", "", Store.YES));
		fields.put("shortDesc", new TextField("sd", "", Store.NO));
		fields.put("description", new TextField("d", "", Store.NO));
		fields.put("category", new TextField("c", "", Store.NO));
		fields.put("tags", new TextField("t", "", Store.NO));
		fields.put("attributes", new TextField("a", "", Store.NO));
	}
	private static Document createDocument(Shop shop) {
		Document doc = new Document();
		fields.get("id").setIntValue(shop.getId());
		fields.get("name").setStringValue(shop.getName());
		fields.get("shortDesc").setStringValue(shop.getShortDescription());
		fields.get("description").setStringValue(shop.getDescription() == null ? "" : shop.getDescription());
		fields.get("category").setStringValue(shop.getCategory().getElementDisplayName());
		fields.get("tags").setStringValue(shop.getTags().stream().map(Tag::getDisplayName).collect(Collectors.joining(" ")));
		MultiMap<IAttribute, AttributeValue, Set<AttributeValue>> attributes = shop.getAttributes();
		String attributeKeys = attributes.keySet().stream().map(IAttribute::getDisplayName).collect(Collectors.joining(" "));
		String attributeValues = attributes.values().stream().filter(Objects::nonNull).map(AttributeValue::getDisplayName).collect(Collectors.joining(" "));
		fields.get("attributes").setStringValue((attributeKeys + " " + attributeValues).trim());
		doc.add(fields.get("id"));
		doc.add(fields.get("name"));
		doc.add(fields.get("shortDesc"));
		doc.add(fields.get("description"));
		doc.add(fields.get("category"));
		doc.add(fields.get("tags"));
		doc.add(fields.get("attributes"));
		return doc;
	}

	private IndexWriter indexWriter(Directory indexDirectory) throws IOException {
		IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
		conf.setOpenMode(OpenMode.CREATE);
		return new IndexWriter(indexDirectory, conf);
	}

}
