package com.antares.mangareader.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.antares.jahamanga.provider.DbMangaReader;

public class PageModel {
	public String pageNumber;
	public String url;
	
	public PageModel() {
		// TODO Auto-generated constructor stub
	}
	
	public PageModel(List<String> model) {
		pageNumber = model.get(0);
		url = model.get(1);
	}
	
	public static List<PageModel> createFromList(List<List<String>> list){
		List<PageModel> model = new ArrayList<PageModel>();
		if (list != null){
			for (int i = 0; i < list.size(); i++) {
				model.add(new PageModel(list.get(i)));
			}
		}
		return model;
	}
	
	public ContentValues createContentValues(String chapterId){
		ContentValues values = new ContentValues();
		values.put(DbMangaReader.Tb_Chapter_Page_Detail.CHAPTER_ID, chapterId);
		values.put(DbMangaReader.Tb_Chapter_Page_Detail._ID, pageNumber);
		values.put(DbMangaReader.Tb_Chapter_Page_Detail.IMAGE, url);
		return values;
	}
	
	
	public static PageModel fromCursor(Cursor cursor){
		PageModel model = new PageModel();
		if (cursor != null){
			model.pageNumber = cursor.getString(1);
			model.url = cursor.getString(2);
		}
		return model;
	}
	
}
