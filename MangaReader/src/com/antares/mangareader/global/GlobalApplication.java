package com.antares.mangareader.global;

import java.io.InputStream;

import android.app.Application;

import com.antares.jahamanga.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.parse.Parse;
import com.parse.PushService;
import com.squareup.okhttp.OkHttpClient;

public class GlobalApplication extends Application {

	private static Application mContext;
	private OkHttpClient client = new OkHttpClient();


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = this;
		client = new OkHttpClient();
		Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client) );
		PushService.setDefaultPushCallback(this, MainActivity.class);
	}

	public static Application getApplication(){
		return mContext;
	}

}
