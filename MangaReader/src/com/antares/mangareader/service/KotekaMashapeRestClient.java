package com.antares.mangareader.service;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

import com.antares.mangareader.util.AppConstant;

public class KotekaMashapeRestClient {
									   
public static final String BASE_URL = "https://mangareader.p.mashape.com";
	
	private static KotekaMashapeService mMangaService;
	
	public static KotekaMashapeService getMashapeService() {
		if(null == mMangaService) {
			
			RestAdapter.Builder adapterBuilder = new RestAdapter.Builder().setRequestInterceptor(new RequestInterceptor() {
				
				@Override
				public void intercept(RequestFacade request) {
					request.addHeader(AppConstant.KEY_HEADER, AppConstant.VALUE_HEADER);
					
				}
			});
			adapterBuilder.setEndpoint(BASE_URL);
			RestAdapter mAdapter = adapterBuilder .build();
			mMangaService = mAdapter.create(KotekaMashapeService.class);
		}
		return mMangaService;
	}
}
