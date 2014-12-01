package com.antares.mangareader.service;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

import com.antares.mangareader.util.AppConstant;
import com.squareup.okhttp.OkHttpClient;


public class MangaEdenRestClient {
	private static MangaEdenService mMangaService;
	
	public static MangaEdenService getListMangaService() {
		if(null == mMangaService) {
			OkHttpClient client = new OkHttpClient();
			RestAdapter.Builder adapterBuilder = new RestAdapter.Builder().setClient(new OkClient(client));
			adapterBuilder.setEndpoint(AppConstant.URL);
			RestAdapter mAdapter = adapterBuilder.build();
			mMangaService = mAdapter.create(MangaEdenService.class);
		}
		return mMangaService;
	}
}
