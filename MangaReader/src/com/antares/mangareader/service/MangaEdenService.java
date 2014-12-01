package com.antares.mangareader.service;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.antares.mangareader.model.ChapterModelResponse;
import com.antares.mangareader.model.MangaModelResponse;
import com.antares.mangareader.model.PagesModelResponse;


public interface MangaEdenService {
	
	@GET("/list/0")
	void getMangaList(Callback<MangaModelResponse> callback);

	
	@GET("/manga/{id}")
	void getChapterList(@Path("id") String mangaId, Callback<ChapterModelResponse> callback);
	
	@GET("/chapter/{id}")
	void getPagesList(@Path("id") String chapterId, Callback<PagesModelResponse> callback);

	
}
