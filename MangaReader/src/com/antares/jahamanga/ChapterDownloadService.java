package com.antares.jahamanga;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.antares.mangareader.model.PageModel;
import com.antares.mangareader.model.PagesModelResponse;
import com.antares.mangareader.service.MangaEdenRestClient;
import com.antares.mangareader.service.MangaEdenService;
import com.antares.mangareader.util.AppConstant;
import com.antares.mangareader.util.Logger;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

public  class ChapterDownloadService extends IntentService{

	private String TAG = ChapterDownloadService.class.getSimpleName();
	public static final String CHAPTER_ID = "chapter_id";
	public static final String CHAPTER_NUMBER = "chapter_number";
	public static final String MANGA_NAME = "manga_name";
	private MangaEdenService mService;
	private String chapterId;
	private String chapterNumber;
	private String mangaName;

	public ChapterDownloadService() {
		// TODO Auto-generated constructor stub
		super("");
		
	}

	public void DownloadImageFromPath(final String chapterId, final String pageNumber, String url2){
		InputStream in =null;
		int responseCode = -1;
		try{
			final File file = new File(AppConstant.MANGA_FOLDER + File.separator + chapterId);
			final File path = new File(file, pageNumber +  AppConstant.EXTENSION);
			if (!path.exists()){
				Logger.log(TAG, "Download start From "  + url2);
				URL url = new URL(url2);//"http://192.xx.xx.xx/mypath/img1.jpg
				OkHttpClient client = new OkHttpClient();
				Request req = new Request.Builder().url(url2).build();
				com.squareup.okhttp.Response resp = client.newCall(req).execute();
				if (resp.isSuccessful()){
					final Bitmap bmp;
					bmp = BitmapFactory.decodeStream(resp.body().byteStream());
					if (bmp != null) {
						try {
							// build directory
							if (!file.exists()) {
								file.mkdirs();
							}

							// output image to file
							FileOutputStream fos = new FileOutputStream(path);//(AppConstant.MANGA_FOLDER + "/" + chapterId + "/" + pageNumber +  ".png");
							bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
							fos.close();
							Logger.log(TAG, "Success - " + chapterId + "-" + pageNumber);
							
						} catch (Exception e) {
							Logger.log(TAG, "Save Image Exception - " + chapterId + "-" + pageNumber + " : " + e.getMessage());
							e.printStackTrace();
						}

					} else {
						Logger.log(TAG, "parsing image error - " + chapterId + "-" + pageNumber);
					}
				}
				else{
					Logger.log(TAG, "parsing image error - " + chapterId + "-" + pageNumber);
				}
				
				
				Logger.log(TAG, "Download FINISH- " + chapterId + "-" + pageNumber);
			}
			else{
				Logger.log(TAG, "Image Already download- " + chapterId + "-" + pageNumber);
			}
		}
		catch(Exception ex){
			Logger.log("Exception", ex.toString());
		}
	}

	
	public void showNotification(int now, int total){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher_new)
		        .setContentTitle("Downloading " + mangaName + " " + chapterNumber)
		        .setContentText(String.format("%d of %d", now, total));
		// Creates an explicit intent for an Activity in your app

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		// Adds the back stack for the Intent (but not the Intent itself)
		// Adds the Intent that starts the Activity to the top of the stack
		if (total == now && now == 0){
			mBuilder.setProgress(0,0, false);
			mBuilder.setOngoing(false);
            mBuilder.setContentText("Download complete");

		}
		else{
			mBuilder.setOngoing(true);
			mBuilder.setProgress(total, now, true);
		}
		
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(0, mBuilder.build());
	}
	
	Callback<PagesModelResponse> callbackListPage = new Callback<PagesModelResponse>() {

		@Override
		public void success(PagesModelResponse result, Response arg1) {
			Logger.log(ChapterDownloadService.class, "Success : "+new Gson().toJson(result));
			final List<PageModel> model = PageModel.createFromList(result.images);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					int size = 0;
					showNotification(size, model.size());
					for (final PageModel pageModel : model) {
						// AppConstant.URL_IMAGE + pageModel.url=> URL To Download
						//Logger.log(TAG, AppConstant.URL_IMAGE + pageModel.url);
						// TODO Auto-generated method stub
						DownloadImageFromPath(chapterId, pageModel.pageNumber, AppConstant.URL_IMAGE + pageModel.url);
						size++;
						showNotification(size, model.size());
					}
					showNotification(0, 0);
				}
			}).start();
		}

		@Override
		public void failure(RetrofitError arg0) {
			Logger.log(ChapterDownloadService.class, "Failure : " + arg0.getMessage());
		}
	};

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Logger.log(TAG, "Service START" );
		if (intent != null){
			chapterId = intent.getStringExtra(CHAPTER_ID);
			mangaName = intent.getStringExtra(MANGA_NAME);
			chapterNumber= intent.getStringExtra(CHAPTER_NUMBER);
			Logger.log(TAG, "chapterID " + chapterId);
			if (mService == null) {
				mService = MangaEdenRestClient.getListMangaService();
			}
			if (chapterId == null) {
				mService.getPagesList(chapterId,
						callbackListPage);
			} else {
				mService.getPagesList(chapterId, callbackListPage);
			}
		}
		Logger.log(TAG, "Service END " );
	}
	public ChapterDownloadService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

}
