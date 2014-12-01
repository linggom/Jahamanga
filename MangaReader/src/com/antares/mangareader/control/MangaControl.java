package com.antares.mangareader.control;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;

import com.antares.jahamanga.provider.DbMangaReader;
import com.antares.mangareader.global.GlobalApplication;
import com.antares.mangareader.model.ChapterModelResponse;
import com.antares.mangareader.model.MangaDetailModelReponse;
import com.antares.mangareader.model.MangaModelResponse;
import com.antares.mangareader.model.PageModel;
import com.antares.mangareader.model.PagesModelResponse;
import com.antares.mangareader.service.MangaEdenRestClient;
import com.antares.mangareader.service.MangaEdenService;
import com.antares.mangareader.util.Logger;
import com.antares.mangareader.util.Util;
import com.srin.lab4.common.controller.BaseController;
import com.srin.lab4.common.module.IViewController;

public class MangaControl extends BaseController {

	public static final int LOAD_MANGA_ERROR = -1;
	public static final int LOAD_MANGA_SUCCESS = 1;
	public static final int LOAD_MANGA_EMPTY = 2;
	public static final int LOAD_MANGA_DB_SUCCESS = 3;
	public static final int LOAD_MANGA_DB_EMPTY = 4;

	private MangaEdenService mService;

	public MangaControl(IViewController controller) {
		super(controller);
		mService = MangaEdenRestClient.getListMangaService();
	}

	public void getMangaList() {
		if (mService != null) {
			mService.getMangaList(callbackListManga);
		}
	}

	Callback<ChapterModelResponse> callbackChapterList = new Callback<ChapterModelResponse>() {

		@Override
		public void failure(RetrofitError arg0) {
			mIView.afterTask(LOAD_MANGA_EMPTY, null);
		}

		@Override
		public void success(ChapterModelResponse result, Response arg1) {			
			mIView.afterTask(LOAD_MANGA_SUCCESS, result);
		}
	};



	Callback<MangaModelResponse> callbackListManga = new Callback<MangaModelResponse>() {

		@Override
		public void success(final MangaModelResponse result, Response arg1) {

			if (Util.isListValid(result.manga)) {
				final List<MangaDetailModelReponse> list = (List<MangaDetailModelReponse>) result.manga;
				AsyncTask<Void, Void, Cursor> a = new AsyncTask<Void, Void, Cursor>(){

					@Override
					protected Cursor doInBackground(Void... params) {
						// TODO Auto-generated method stub
						ContentResolver resolver = GlobalApplication.getApplication().getContentResolver();
						ContentValues values;
						for (MangaDetailModelReponse mangaDetailModelReponse : list) {
							values = mangaDetailModelReponse.toContentValues();
							resolver.insert(DbMangaReader.Tb_Manga.URI, values);
						}
						Cursor cursor = GlobalApplication
								.getApplication()
								.getContentResolver()
								.query(DbMangaReader.Tb_Manga.URI, null, null,
										null, null);

						return cursor;
					}
					protected void onPostExecute(Cursor cursor) {
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								mIView.afterTask(LOAD_MANGA_DB_SUCCESS, cursor);
							} else {
								mIView.afterTask(LOAD_MANGA_EMPTY, null);
							}
						} else {
							mIView.afterTask(LOAD_MANGA_EMPTY, null);
						}
					};

				};
				a.execute();
			} else {
				mIView.afterTask(LOAD_MANGA_EMPTY, null);
			}
		}

		@Override
		public void failure(RetrofitError arg0) {
			mIView.afterTask(LOAD_MANGA_ERROR, null);
		}
	};

	Callback<PagesModelResponse> callbackListPage = new Callback<PagesModelResponse>() {

		@Override
		public void success(PagesModelResponse result, Response arg1) {

			if (Util.isListValid(result.images)) {
				mIView.afterTask(LOAD_MANGA_SUCCESS, result.images);
			} else {
				mIView.afterTask(LOAD_MANGA_EMPTY, null);
			}
		}

		@Override
		public void failure(RetrofitError arg0) {
			mIView.afterTask(LOAD_MANGA_ERROR, null);
		}
	};

	public void getChapterList(String mangaId) {
		if (mService != null) {
			if (mangaId == null) {
				mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
			} else {

				mService.getChapterList(mangaId, callbackChapterList);
			}
		}

	}

	public void searchManga(final String keyword){
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				Cursor cursor = GlobalApplication
						.getApplication()
						.getContentResolver()
						.query(DbMangaReader.Tb_Manga.URI_SEARCH, null, keyword, null,
								null);
				if (cursor != null) {
					if (cursor.moveToFirst()) {
						mIView.afterTask(LOAD_MANGA_DB_SUCCESS, cursor);
					} else {
						mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
					}
				} else {
					mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
				}
			}
		});
	}
	public void loadMangaFromDb() {		
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				Cursor cursor = GlobalApplication
						.getApplication()
						.getContentResolver()
						.query(DbMangaReader.Tb_Manga.URI, null, null, null,
								null);
				if (cursor != null) {
					if (cursor.moveToFirst()) {
						mIView.afterTask(LOAD_MANGA_DB_SUCCESS, cursor);
					} else {
						mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
					}
				} else {
					mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
				}
			}
		});
	}

	public void getPageList(String chapterId) {
		Logger.log(this, "getPageList("+chapterId+")");
		if (mService != null) {
			if (chapterId == null) {
				mService.getPagesList(chapterId,
						callbackListPage);
			} else {
				mService.getPagesList(chapterId, callbackListPage);
			}
		}

	}

	public void saveMangaHeaderToDB(final ChapterModelResponse response, final String mangaId){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				GlobalApplication.getApplication().getContentResolver().insert(DbMangaReader.Tb_Chapter_Manga_Header.URI, response.toContentValues(mangaId));			
				for (ContentValues  cv : response.detailContentValues(mangaId)) {
					GlobalApplication.getApplication().getContentResolver().insert(DbMangaReader.Tb_Chapter_Manga_Detail.URI, cv);
				}
			}
		}).start();
	}

	public void getChapterListFromDb(final String mangaId) {
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				ChapterModelResponse model; 
				Cursor cursor = GlobalApplication
						.getApplication()
						.getContentResolver()
						.query(DbMangaReader.Tb_Chapter_Manga_Header.URI, null, mangaId, null,
								null);

				if (cursor != null) {

					if (cursor.moveToFirst()) {
						model = ChapterModelResponse.fromCursor(cursor);
						cursor = GlobalApplication
								.getApplication()
								.getContentResolver()
								.query(DbMangaReader.Tb_Chapter_Manga_Detail.URI, null, mangaId, null,
										null);
						if (cursor != null){
							if (cursor.moveToFirst()){
								Logger.log(this, "Success");
								model.listChapterModels = ChapterModelResponse.getChaptersFromCursor(cursor);
								mIView.afterTask(LOAD_MANGA_DB_SUCCESS, model);
							}
							else{
								Logger.log(this, "Empty when load list chapter");
								mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
							}
						}
						else{
							Logger.log(this, "Empty when load list chapter");
							mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
						}
					} else {
						mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
					}
				} else {
					mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
				}

			}
		});
	}

	public void getPageFromDb(String chapterId){
		Logger.log(this, "getPageFromDb("+chapterId+")");
		Cursor cursor = GlobalApplication
				.getApplication()
				.getContentResolver()
				.query(DbMangaReader.Tb_Chapter_Page_Detail.URI, null, chapterId, null,
						null);
		ArrayList<PageModel> model = new ArrayList<PageModel>();
		if (cursor != null) {

			if (cursor.moveToFirst()) {
				do{
					model.add(PageModel.fromCursor(cursor));
				}while(cursor.moveToNext());
				cursor.close();
				mIView.afterTask(LOAD_MANGA_DB_SUCCESS, model);
			} else {
				Logger.log(this, "Empty get page list from db cursor is null " + chapterId);
				mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
			}
		} else {
			Logger.log(this, "Empty get page list from db " + chapterId);
			mIView.afterTask(LOAD_MANGA_DB_EMPTY, null);
		}
	}

	public void savePageToDb(final String chapterId, final List<PageModel> model) {
		// TODO Auto-generated method stub
		Logger.log(this, "savePageToDb");
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (PageModel pageModel : model) {
					GlobalApplication
					.getApplication()
					.getContentResolver()
					.insert(DbMangaReader.Tb_Chapter_Page_Detail.URI, pageModel.createContentValues(chapterId));
				}
			}
		}).start();
	}

}
