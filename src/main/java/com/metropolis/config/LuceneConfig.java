package com.metropolis.config;

import java.io.IOException;
import java.nio.file.FileSystems;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LuceneConfig {
	@Bean
	public Directory getIndexDirectory() throws IOException {
		return FSDirectory.open(FileSystems.getDefault().getPath("F:", "Dev", "lucene", "indexes", "metropolis"));
	}
}
