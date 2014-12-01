package com.antares.mangareader.model;

import java.util.List;

public class KotekaMashapeMangaModelResponse {
	public String memory_usage;
	public List<KotekaMashapeMangaModel> data;
	
	public class KotekaMashapeMangaModel {
		public String id;
		public String title;
		public String lastChapter;
		public String img;
		public String date;
	}
}
