package com.antares.mangareader.service;

import retrofit.Callback;
import retrofit.http.GET;

import com.antares.mangareader.model.KotekaMashapeMangaModelResponse;

public interface KotekaMashapeService {

	@GET("/getFavorit/")
	void getFavouriteMangaList(Callback<KotekaMashapeMangaModelResponse> callback);
}
