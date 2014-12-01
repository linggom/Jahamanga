package com.antares.jahamanga.provider;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class MangaReaderContentProvider extends ContentProvider {

	public static final String QUERY_CREATE_TB_MANGA = 
			"create table " + DbMangaReader.Tb_Manga.NAME + "( " 
			+ DbMangaReader.Tb_Manga._ID  + " text PRIMARY KEY , "
			+ DbMangaReader.Tb_Manga.ALIAS  + " text , "
			+ DbMangaReader.Tb_Manga.HITS  + " integer , "
			+ DbMangaReader.Tb_Manga.IMAGE  + " text , "
			+ DbMangaReader.Tb_Manga.LAST_CHAPTER_DATE  + " long, "
			+ DbMangaReader.Tb_Manga.STATUS + " integer, "
			+ DbMangaReader.Tb_Manga.TITLE  + " text"
			+ " )";

	public static final String QUERY_CREATE_TB_FAVOURITE_MANGA = 
			"create table " + DbMangaReader.Tb_Favourite_Manga.NAME + "( " 
			+ DbMangaReader.Tb_Favourite_Manga._ID  + " text PRIMARY KEY , "
			+ DbMangaReader.Tb_Favourite_Manga.ALIAS  + " text , "
			+ DbMangaReader.Tb_Favourite_Manga.HITS  + " integer , "
			+ DbMangaReader.Tb_Favourite_Manga.IMAGE  + " text , "
			+ DbMangaReader.Tb_Favourite_Manga.LAST_CHAPTER_DATE  + " long, "
			+ DbMangaReader.Tb_Favourite_Manga.STATUS + " integer, "
			+ DbMangaReader.Tb_Favourite_Manga.TITLE  + " text"
			+ " )";
	
	public static final String QUERY_CREATE_TB_CHAPTER_MANGA_HEADER = 
			"create table " + DbMangaReader.Tb_Chapter_Manga_Header.NAME + "( " 
			+ DbMangaReader.Tb_Chapter_Manga_Header.MANGAID  + " text PRIMARY KEY , "
			+ DbMangaReader.Tb_Chapter_Manga_Header.AUTHOR  + " text , "
			+ DbMangaReader.Tb_Chapter_Manga_Header.DESCRIPTION  + " text, "
			+ DbMangaReader.Tb_Chapter_Manga_Header.IMAGE  + " text , "
			+ DbMangaReader.Tb_Chapter_Manga_Header.NAME + " text, "
			+ DbMangaReader.Tb_Chapter_Manga_Header.RELEASED + " integer, "
			+ DbMangaReader.Tb_Chapter_Manga_Header.TITLE  + " text"
			+ " )";
	
	
	public static final String QUERY_CREATE_TB_CHAPTER_MANGA_DETAIL = 
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
	
	public static final String QUERY_CREATE_TB_CHAPTER_PAGE_DETAIL = 
			"create table " + DbMangaReader.Tb_Chapter_Page_Detail.NAME + "( " 
			+ DbMangaReader.Tb_Chapter_Page_Detail.CHAPTER_ID  + " text , "
			+ DbMangaReader.Tb_Chapter_Page_Detail._ID  + " text , "
			+ DbMangaReader.Tb_Chapter_Page_Detail.IMAGE  + " text , "
			+ DbMangaReader.Tb_Chapter_Page_Detail.LOCALURI  + " text , "
			+ "primary key ("+DbMangaReader.Tb_Chapter_Page_Detail.CHAPTER_ID
			+ ", "+ DbMangaReader.Tb_Chapter_Page_Detail._ID  +")"
			+ " )";
	
	

	public static final String QUERY_DROP_TABLE_MANGA = "drop table if exists " + DbMangaReader.Tb_Manga.NAME;
	public static final String QUERY_DROP_TABLE_FAVOURITE_MANGA = "drop table if exists " + DbMangaReader.Tb_Favourite_Manga.NAME;
	public static final String QUERY_DROP_TABLE_CHAPTER_MANGA_HEADER = "drop table if exists " + DbMangaReader.Tb_Chapter_Manga_Header.NAME;
	public static final String QUERY_DROP_TABLE_CHAPTER_MANGA_DETAIL = "drop table if exists " + DbMangaReader.Tb_Chapter_Manga_Detail.NAME;
	public static final String QUERY_DROP_TABLE_CHAPTER_PAGE_DETAIL = "drop table if exists " + DbMangaReader.Tb_Chapter_Page_Detail.NAME;



	private static class DatabaseHelper extends SQLiteOpenHelper{

		public DatabaseHelper(Context context) {
			super(context, DbMangaReader.NAME, null, DbMangaReader.VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(MangaReaderContentProvider.QUERY_CREATE_TB_MANGA);
			db.execSQL(MangaReaderContentProvider.QUERY_CREATE_TB_FAVOURITE_MANGA);
			db.execSQL(MangaReaderContentProvider.QUERY_CREATE_TB_CHAPTER_MANGA_HEADER);
			db.execSQL(MangaReaderContentProvider.QUERY_CREATE_TB_CHAPTER_MANGA_DETAIL);
			db.execSQL(MangaReaderContentProvider.QUERY_CREATE_TB_CHAPTER_PAGE_DETAIL);
			
			db.execSQL(DbMangaReader.generateFavouriteManga());
			
			

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL(QUERY_DROP_TABLE_FAVOURITE_MANGA);
			db.execSQL(QUERY_DROP_TABLE_MANGA);
			db.execSQL(QUERY_DROP_TABLE_CHAPTER_MANGA_HEADER);
			db.execSQL(QUERY_DROP_TABLE_CHAPTER_MANGA_DETAIL);
			db.execSQL(QUERY_DROP_TABLE_CHAPTER_PAGE_DETAIL);
			

			onCreate(db);

		}

	}


	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private SQLiteDatabase db;

	private static final int CODE_TB_MANGA = 10001;
	private static final int CODE_TB_FAVOURITE_MANGA = 10002;
	private static final int CODE_SEARCH_MANGA = 10003;
	private static final int CODE_CHAPTER_LISTMANGA = 10004;
	private static final int CODE_HEADER_MANGA = 10005;
	private static final int CODE_PAGE_DETAIL = 10006;
	
	/*
	 * Step by step to create a table 
	 * 1. Create a table model in DbMangaReaderClass
	 * 2. Create a query to create and drop  table in db
	 * 3. Create code for URI matcher
	 * 4. add code to uri matcher in static
	 * 5. add code matcher to getType function
	 * 6. create query to load data in query or insert or update 
	 * 
	 */
	
	static{
		uriMatcher.addURI(DbMangaReader.AUTHORITY, DbMangaReader.Tb_Manga.NAME, CODE_TB_MANGA);
		uriMatcher.addURI(DbMangaReader.AUTHORITY, DbMangaReader.Tb_Favourite_Manga.NAME, CODE_TB_FAVOURITE_MANGA);
		uriMatcher.addURI(DbMangaReader.AUTHORITY, DbMangaReader.Tb_Manga.SEARCH, CODE_SEARCH_MANGA);
		uriMatcher.addURI(DbMangaReader.AUTHORITY, DbMangaReader.Tb_Chapter_Manga_Header.NAME, CODE_HEADER_MANGA);
		uriMatcher.addURI(DbMangaReader.AUTHORITY, DbMangaReader.Tb_Chapter_Manga_Detail.NAME, CODE_CHAPTER_LISTMANGA);
		uriMatcher.addURI(DbMangaReader.AUTHORITY, DbMangaReader.Tb_Chapter_Page_Detail.NAME, CODE_PAGE_DETAIL);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		int match = uriMatcher.match(uri);
		switch (match) {
		case CODE_TB_MANGA:
			return DbMangaReader.AUTHORITY + DbMangaReader.SEPARATOR + DbMangaReader.Tb_Manga.NAME;
		case CODE_TB_FAVOURITE_MANGA:
			return DbMangaReader.AUTHORITY + DbMangaReader.SEPARATOR + DbMangaReader.Tb_Favourite_Manga.NAME;
		case CODE_SEARCH_MANGA:
			return DbMangaReader.AUTHORITY + DbMangaReader.SEPARATOR + DbMangaReader.Tb_Manga.SEARCH;
		case CODE_HEADER_MANGA:
			return DbMangaReader.AUTHORITY + DbMangaReader.SEPARATOR + DbMangaReader.Tb_Chapter_Manga_Header.NAME;
		case CODE_CHAPTER_LISTMANGA:
			return DbMangaReader.AUTHORITY + DbMangaReader.SEPARATOR + DbMangaReader.Tb_Chapter_Manga_Detail.NAME;
		case CODE_PAGE_DETAIL:
			return DbMangaReader.AUTHORITY + DbMangaReader.SEPARATOR + DbMangaReader.Tb_Chapter_Page_Detail.NAME;
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri recordUri = null;
		int match = uriMatcher.match(uri);
		long rowId = 0;
		switch (match) {
		case CODE_TB_MANGA:
			rowId = db.insertWithOnConflict(DbMangaReader.Tb_Manga.NAME, DbMangaReader.Tb_Manga._ID, values, SQLiteDatabase.CONFLICT_REPLACE);
			recordUri = ContentUris.withAppendedId(DbMangaReader.Tb_Manga.URI, rowId);
			break;
		case CODE_TB_FAVOURITE_MANGA:
			rowId = db.insertWithOnConflict(DbMangaReader.Tb_Favourite_Manga.NAME, DbMangaReader.Tb_Favourite_Manga._ID, values, SQLiteDatabase.CONFLICT_REPLACE);
			recordUri = ContentUris.withAppendedId(DbMangaReader.Tb_Favourite_Manga.URI, rowId);
			break;
		case CODE_HEADER_MANGA:
			rowId = db.insertWithOnConflict(DbMangaReader.Tb_Chapter_Manga_Header.NAME, DbMangaReader.Tb_Chapter_Manga_Header.MANGAID, values, SQLiteDatabase.CONFLICT_REPLACE);
			recordUri = ContentUris.withAppendedId(DbMangaReader.Tb_Chapter_Manga_Header.URI, rowId);
			break;
		case CODE_CHAPTER_LISTMANGA:
			rowId = db.insertWithOnConflict(DbMangaReader.Tb_Chapter_Manga_Detail.NAME, DbMangaReader.Tb_Chapter_Manga_Detail._ID, values, SQLiteDatabase.CONFLICT_IGNORE);
			recordUri = ContentUris.withAppendedId(DbMangaReader.Tb_Chapter_Manga_Detail.URI, rowId);
			break; 
		case CODE_PAGE_DETAIL:
			db.insert(DbMangaReader.Tb_Chapter_Page_Detail.NAME, null, values);
			break;
		default:
			break;
		}
		return recordUri;
	}

	 
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		DatabaseHelper helper = new DatabaseHelper(getContext());
		db = helper.getWritableDatabase();
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		int match = uriMatcher.match(uri);
		Cursor cursor = null;
		switch (match) {
		case CODE_TB_MANGA:
			cursor = db.query(DbMangaReader.Tb_Manga.NAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case CODE_TB_FAVOURITE_MANGA:
			cursor = db.query(DbMangaReader.Tb_Favourite_Manga.NAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case CODE_SEARCH_MANGA:
			String query = "select * from " + DbMangaReader.Tb_Manga.NAME +  " where " +
					DbMangaReader.Tb_Manga.TITLE + " like '%" + selection + "%'";
			cursor = db.rawQuery(query, selectionArgs);
			break;
		case CODE_HEADER_MANGA:
			query = "select * from " + DbMangaReader.Tb_Chapter_Manga_Header.NAME +  " where " + DbMangaReader.Tb_Chapter_Manga_Header.MANGAID + " like '%" + selection + "%'";
			cursor = db.rawQuery(query, selectionArgs);
			break;
		case CODE_CHAPTER_LISTMANGA:
			query = "select * from " 
					+ DbMangaReader.Tb_Chapter_Manga_Detail.NAME  
					+ " where " + DbMangaReader.Tb_Chapter_Manga_Detail.MANGAID 
					+ " like '%" + selection + "%'"
					+ " order by " + DbMangaReader.Tb_Chapter_Manga_Detail.NUMBER + " desc " ;
			cursor = db.rawQuery(query, selectionArgs);
			break;
		case CODE_PAGE_DETAIL:
			query = "select * from " 
					+ DbMangaReader.Tb_Chapter_Page_Detail.NAME  
					+  " where " 
					+ DbMangaReader.Tb_Chapter_Page_Detail.CHAPTER_ID 
					+ " like '%" + selection + "%'";
			cursor = db.rawQuery(query, selectionArgs);
			break;
		default:
			cursor = null;
			break;
		}
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}


