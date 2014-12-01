package com.antares.mangareader.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.antares.jahamanga.provider.DbMangaReader;
import com.google.gson.annotations.SerializedName;

public class MangaDetailModelReponse {
	
	@SerializedName("i") private String id;
	@SerializedName("h") private String hits;
	@SerializedName("t") private String title;
	@SerializedName("im") private String img;
	@SerializedName("ld") private long lastdate;
	@SerializedName("s") private int status;
	@SerializedName("a") private String alias;
	

	public MangaDetailModelReponse() {
		// TODO Auto-generated constructor stub
	}
	
	
	/*
	 * Get data from cursor 
	 * But not recomended to convert data from cursor because it can reduce the performance
	 */
	public MangaDetailModelReponse(Cursor cursor) { 		
		setId(cursor.getString(0));
		setAlias(cursor.getString(1));
		setHits(cursor.getString(2));
		setImg(cursor.getString(3));
		setLastdate(cursor.getLong(4));
		setStatus(cursor.getInt(5));
		setTitle(cursor.getString(6));
	}
	

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getLastdate() {
		return lastdate;
	}
	public void setLastdate(long lastdate) {
		this.lastdate = lastdate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public ContentValues toContentValues(){
		ContentValues cv = new ContentValues();
		cv.put(DbMangaReader.Tb_Manga._ID, getId());
		cv.put(DbMangaReader.Tb_Manga.ALIAS, getAlias());
		cv.put(DbMangaReader.Tb_Manga.HITS, getHits());
		cv.put(DbMangaReader.Tb_Manga.IMAGE, getImg());
		cv.put(DbMangaReader.Tb_Manga.LAST_CHAPTER_DATE, getLastdate());
		cv.put(DbMangaReader.Tb_Manga.TITLE, getTitle());
		cv.put(DbMangaReader.Tb_Manga.STATUS, getStatus());
		return cv;
	}
	
	public static ContentValues toContentValues(MangaDetailModelReponse manga){
		ContentValues cv = new ContentValues();
		cv.put(DbMangaReader.Tb_Manga._ID, manga.getId());
		cv.put(DbMangaReader.Tb_Manga.ALIAS, manga.getAlias());
		cv.put(DbMangaReader.Tb_Manga.HITS, manga.getHits());
		cv.put(DbMangaReader.Tb_Manga.IMAGE, manga.getImg());
		cv.put(DbMangaReader.Tb_Manga.LAST_CHAPTER_DATE, manga.getLastdate());
		cv.put(DbMangaReader.Tb_Manga.TITLE, manga.getTitle());
		cv.put(DbMangaReader.Tb_Manga.STATUS, manga.getStatus());
		return cv;
	}
	
	
	
	
}
