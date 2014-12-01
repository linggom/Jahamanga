package com.antares.mangareader.adapter;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.antares.jahamanga.R;
import com.antares.jahamanga.provider.DbMangaReader;
import com.antares.mangareader.util.Style;
import com.antares.mangareader.util.Style.Font.FontType;
import com.antares.mangareader.util.Util;

public class ChapterAdapter extends BaseAdapter {

	private Context mContext;
	private List<List<String>> listOfChapters;
	private List<ChapterModel> listOfChapterModels;
	private LayoutInflater mInflater;
	private String mangaTitle;
	private boolean mModelAlreadyParse = false;
	
	public boolean isModelAlreadyParse() {
		return mModelAlreadyParse;
	}

	public void setModelAlreadyParse(boolean mModelAlreadyParse) {
		this.mModelAlreadyParse = mModelAlreadyParse;
	}

	public ChapterAdapter(Context context) {
		this.mContext = context;
		this.mInflater = Style.getGlobalLayoutInflater(mContext);
	}

	public void setMangaTitle(String title){
		this.mangaTitle = title;
	}
	public void setData(List<List<String>>listOfChapters) {
		setModelAlreadyParse(false);
		if (this.listOfChapters == null) {
			this.listOfChapters = listOfChapters;
		} else {
			this.listOfChapters.addAll(listOfChapters);
		}
		notifyDataSetChanged();
	}
	
	public void setData(boolean modelAlreadyParse, List<ChapterModel>listOfChapters) {
		if (Util.isListValid(listOfChapters)){
			if (this.listOfChapterModels == null) {
				this.listOfChapterModels = listOfChapters;
			} else {
				this.listOfChapterModels.addAll(listOfChapters);
			}
		}
		setModelAlreadyParse(modelAlreadyParse);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (mModelAlreadyParse){
			return (this.listOfChapterModels == null)? 0 : listOfChapterModels.size();
		}
		else
		{
			return (this.listOfChapters == null)? 0 : listOfChapters.size();
		}
	}

	@Override
	public List<String> getItem(int arg0) {
		// TODO Auto-generated method stub
		return (listOfChapters == null) ? null : listOfChapters.get(arg0);
	}
	
	public ChapterModel getItem2(int arg){
		return (listOfChapterModels == null) ? null : listOfChapterModels.get(arg);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChapterItemCache cache = new ChapterItemCache();
		ChapterModel model;
		if(mModelAlreadyParse ){
			model = listOfChapterModels.get(position);
		}
		else{
			List<String> manga = getItem(position);
			model = new ChapterModel(manga);
		}
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_chapter_list, parent,
					false);
			cache.number = (TextView) convertView.findViewById(R.id.tvNumber);
			cache.title = (TextView) convertView.findViewById(R.id.tvTitle);
			Style.applyTypeFace(mContext, FontType.RobotoLight, cache.title);
			convertView.setTag(cache);
		} else {
			cache = (ChapterItemCache) convertView.getTag();
		}
		cache.title.setText("" + mangaTitle + " " + model.chapterTitle);
		cache.number.setText("Chapter " + model.chapterNumber);
		return convertView;
	}
	
	public static class ChapterModel{
		
		public ChapterModel(List<String> data) {
			if (data != null){
				chapterNumber = data.get(0);
				chapterDate = data.get(1);
				chapterTitle = data.get(2);
				chapterId = data.get(3);
				chapterTitle = (chapterTitle == null || chapterTitle.equals("null")) ? chapterNumber : chapterTitle; 
			}
		}
		
		/*
		 * public static final String QUERY_CREATE_TB_CHAPTER_MANGA_DETAIL = 
			"create table " + DbMangaReader.Tb_Chapter_Manga_Detail.NAME + "( " 
			+ DbMangaReader.Tb_Chapter_Manga_Detail.MANGAID  + " text , "
			+ DbMangaReader.Tb_Chapter_Manga_Detail._ID  + " text , "
			+ DbMangaReader.Tb_Chapter_Manga_Detail.DATE  + " text , "
			+ DbMangaReader.Tb_Chapter_Manga_Detail.NAME  + " text , "
			+ DbMangaReader.Tb_Chapter_Manga_Detail.NUMBER + " integer, "
			+ DbMangaReader.Tb_Chapter_Manga_Header.RELEASED + " integer, "
			+ DbMangaReader.Tb_Chapter_Manga_Header.TITLE  + " text, "
			+ "primary key ("+DbMangaReader.Tb_Chapter_Manga_Detail.MANGAID
			+ ", "+ DbMangaReader.Tb_Chapter_Manga_Detail._ID  +")"
			+ " )";
		 */
		public ChapterModel(Cursor cursor) {
			if (cursor != null){
				chapterNumber = cursor.getString(4);
				chapterDate = cursor.getString(2);
				chapterTitle = cursor.getString(3);
				chapterId = cursor.getString(1);
				chapterTitle = (chapterTitle == null || chapterTitle.equals("null")) ? chapterNumber : chapterTitle; 
			}
		}
		
		public ContentValues toContentValues(String mangaId){
			ContentValues cv = new ContentValues();
			cv.put(DbMangaReader.Tb_Chapter_Manga_Detail.MANGAID, mangaId);
			cv.put(DbMangaReader.Tb_Chapter_Manga_Detail._ID, chapterId);
			cv.put(DbMangaReader.Tb_Chapter_Manga_Detail.NUMBER, chapterNumber);
			cv.put(DbMangaReader.Tb_Chapter_Manga_Detail.DATE, chapterDate);
			cv.put(DbMangaReader.Tb_Chapter_Manga_Detail.TITLE, chapterTitle);
			return cv;
		}
		
		public String chapterNumber;
		public String chapterDate;
		public String chapterTitle;
		public String chapterId;
	}

	public static class ChapterItemCache {
		public TextView title;
		public TextView number;
	}
}
