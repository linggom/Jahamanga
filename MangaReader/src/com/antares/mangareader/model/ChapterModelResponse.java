package com.antares.mangareader.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.antares.jahamanga.provider.DbMangaReader;
import com.antares.mangareader.adapter.ChapterAdapter;

public class ChapterModelResponse {
	public String chapters_len;
	public String description;
	public String imageURL;
	public String image;
	public String released;
	public String title;
	public String author;
	public List<List<String>> chapters;
	public List<ChapterAdapter.ChapterModel> listChapterModels;
	
	
	public static ChapterModelResponse fromCursor(Cursor cursor){
		ChapterModelResponse obj = new ChapterModelResponse();		
		if (cursor != null){
			obj.author = cursor.getString(1);
			obj.description = cursor.getString(2);
			obj.imageURL  = cursor.getString(3);
			obj.title = cursor.getString(4);
			obj.released = cursor.getString(5);
			return obj;
		}
		return null;
		
	}
	
	public static List<ChapterAdapter.ChapterModel> getChaptersFromCursor(Cursor cursor ){
		List<ChapterAdapter.ChapterModel> listOfModels = new ArrayList<ChapterAdapter.ChapterModel>();
		if (cursor != null){
			if (cursor.moveToFirst()){
				do{
					ChapterAdapter.ChapterModel modelChapter = new ChapterAdapter.ChapterModel(cursor);
					listOfModels.add(modelChapter);
				}while(cursor.moveToNext());
				cursor.close();
			}
		}
		return listOfModels;
	}
	
	
	public ContentValues toContentValues(String mangaId){
		ContentValues values = new ContentValues();
		/*+ DbMangaReader.Tb_Chapter_Manga_Header.MANGAID  + " text PRIMARY KEY , "
			+ DbMangaReader.Tb_Chapter_Manga_Header.AUTHOR  + " text , "
			+ DbMangaReader.Tb_Chapter_Manga_Header.DESCRIPTION  + " text, "
			+ DbMangaReader.Tb_Chapter_Manga_Header.IMAGE  + " text , "
			+ DbMangaReader.Tb_Chapter_Manga_Header.NAME + " long, "
			+ DbMangaReader.Tb_Chapter_Manga_Header.RELEASED + " integer, "
			+ DbMangaReader.Tb_Chapter_Manga_Header.TITLE  + " text"
			*/
		values.put(DbMangaReader.Tb_Chapter_Manga_Header.MANGAID, mangaId);
		values.put(DbMangaReader.Tb_Chapter_Manga_Header.AUTHOR, author);
		values.put(DbMangaReader.Tb_Chapter_Manga_Header.DESCRIPTION, description);
		values.put(DbMangaReader.Tb_Chapter_Manga_Header.IMAGE, imageURL);
		values.put(DbMangaReader.Tb_Chapter_Manga_Header.NAME, title);
		values.put(DbMangaReader.Tb_Chapter_Manga_Header.TITLE, title);
		values.put(DbMangaReader.Tb_Chapter_Manga_Header.RELEASED, released);
		
		
		return values;
	}
	
	public ArrayList<ContentValues>  detailContentValues(String mangaId){
		ArrayList<ContentValues> a = new ArrayList<ContentValues>();
		
		
			for (List<String> data : chapters) {
				a.add(new ChapterAdapter.ChapterModel(data).toContentValues(mangaId));
			}
		
		return a;
	}
}
