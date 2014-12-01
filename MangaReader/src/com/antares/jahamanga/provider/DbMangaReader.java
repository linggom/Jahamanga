package com.antares.jahamanga.provider;

import android.net.Uri;


public class DbMangaReader {
	
	
	
	public static final String NAME = "mangareader";

	public static final String AUTHORITY = "com.antares.jahamanga";
	
	public static final int VERSION = 4;
	
	public static final String SEPARATOR = "/";
	
	public static class Tb_Manga{
		
		

		
		public static final String NAME = "tb_manga";
		public static final String SEARCH = "MANGA_SEARCH";
		
		public static final String _ID = "_id";
		public static final String TITLE = "manga_title";
		public static final String IMAGE = "manga_image";
		public static final String ALIAS = "manga_alias";
		public static final String LAST_CHAPTER_DATE = "manga_last_chapter";
		public static final String HITS = "manga_hits";
		public static final String STATUS = "manga_status";
		
		public static final Uri URI = Uri.parse("content://"+AUTHORITY+"/" + NAME);
		public static final Uri URI_SEARCH = Uri.parse("content://"+AUTHORITY+"/" + SEARCH);
		
		
		
		
	}
	
	public static class Tb_Chapter_Manga_Header{
		public static final String NAME = "tb_chapter_manga_header";
		
		public static final String MANGAID = "manga_id";		
		public static final String DESCRIPTION = "description";
		public static final String IMAGE = "image";
		public static final String TITLE = "title";
		public static final String AUTHOR = "author";
		public static final String RELEASED = "released";
		
		public static final Uri URI = Uri.parse("content://"+AUTHORITY+"/" + NAME);
	}
	

	public static class Tb_Chapter_Manga_Detail{
		public static final String NAME = "tb_chapter_manga_detail";
		
		public static final String MANGAID = "manga_id";		
		public static final String _ID = "_id";
		public static final String TITLE = "title";
		public static final String DATE = "date";
		public static final String NUMBER = "number";
		
		public static final Uri URI = Uri.parse("content://"+AUTHORITY+"/" + NAME);
		
	}
	
	public static class Tb_Favourite_Manga{
		
		public static final String NAME = "tb_favourite_manga";
		
		public static final String _ID = "_id";
		public static final String TITLE = "fav_title";
		public static final String IMAGE = "fav_image";
		public static final String ALIAS = "fav_alias";
		public static final String LAST_CHAPTER_DATE = "fav_last_chapter_date";
		public static final String HITS = "fav_hits";
		public static final String STATUS = "fav_status";
		
		public static final Uri URI = Uri.parse("content://"+AUTHORITY+"/" + NAME);
	}
	
	public static class Tb_Chapter_Page_Detail{
		public static final String NAME = "tb_chapter_detail";
		
		public static final String CHAPTER_ID  = "chapter_id";
		public static final String _ID = "_id";
		public static final String IMAGE = "image";
		public static final String LOCALURI = "local_uri";
		
		public static final Uri URI = Uri.parse("content://"+AUTHORITY+"/" + NAME);
	}
	
	public static String generateFavouriteManga(){
		String naruto = "insert into " + DbMangaReader.Tb_Favourite_Manga.NAME + "  select  " +
				"'4e70ea03c092255ef70046f0' as '" + DbMangaReader.Tb_Favourite_Manga._ID + "', " +
				"'naruto' as '" + DbMangaReader.Tb_Favourite_Manga.TITLE +"', " +
				"239321694 as '" + DbMangaReader.Tb_Favourite_Manga.LAST_CHAPTER_DATE +"', " +
				"'d1/d1cd664cefc4d19ec99603983d4e0b934e8bce91c3fccda3914ac029.png' as '" + DbMangaReader.Tb_Favourite_Manga.IMAGE +"', " +
				"1406101968.0 as '" + DbMangaReader.Tb_Favourite_Manga.HITS +"', " +
				"1 as '" + DbMangaReader.Tb_Favourite_Manga.STATUS +"', " +
				"'Naruto' as '" + DbMangaReader.Tb_Favourite_Manga.ALIAS + "' " ;

		
		String onepiece = "Union Select "+ 
				"'4e70ea10c092255ef7004aa2' , " +
				"'one-piece' , " +
				"187118605 , " +
				"'b0/b0ac7f12d2cb0fc07b9418d5544a3f97cbbc30e967396ae70f98d101.png' , " +
				"1406109150.0 , " +
				"1 , " +
				"'One Piece'  ";
		
		String fairyTail = 
				"Union Select "+ 
						"'4e70ea1dc092255ef7004d5c' , " +
						"'fairy-tail' , " +
						"79621191 , " +
						"'d5/d5d504279e9f99ac5270b098696a203535f55008064142c4fb321405.png' , " +
						"1406277536.0 , " +
						"1 , " +
						"'Fairy Tail'  ";
		
		String conan = 
				"Union Select "+ 
						"'4e70ea0cc092255ef70049ac' , " +
						"'detective-conan' , " +
						"52466975 , " +
						"'11/11e3c18bc3441be8c04fecd4a314b9d3b8bce62a460ab3cfb85ef5fc.jpg',  " +
						"1406187335.0 , " +
						"1 , " +
						"'Detective Conan' " ;
		
		
		String bleach =
				"Union Select "+ 
						"'4e70e9efc092255ef7004274' , " +
						"'bleach' , " +
						"166537209 , " +
						"'99/99af14772b89d87e6f3deb7d6b174537908a3fc5e7cc7eb6fbf92a68.jpg' , " +
						"1406104439.0 , " +
						"1 , " +
						"'Bleach'  " ;
		
		String beelzebub = 
				"Union Select "+ 
						"'4e70ea03c092255ef7004716' , " +
						"'beelzebub' , " +
						"32792772 , " +
						"'3b/3b36544ba7b705d6a542c60871b5e05eba76d0e5f3e81c1190be1822.jpg' , " +
						"1392856434.0 , " +
						"1 , " +
						"'Beelzebub' " ;
		
		String hunterxhunter = 
				"Union Select "+ 
						"'4e70ea02c092255ef70046e0' , " +
						"'hunter-x-hunter' , " +
						"13286719 , " +
						"'df/dfa10afb26c40498e742a80653d23aa0d5ce96cd9c6b6e3f65d09507.jpg' , " +
						"1406130069.0 , " +
						"1 , " +
						"'Hunter x Hunter'  " ;
		
		
		String doraemon = "Union Select "+ 
				"'4e70ea1ec092255ef7004d98' , " +
				"'doraemon' , " +
				"467049 , " +
				"'09/099546f2f3589490f6bffd72d531cd86f543796d1b3b2e780d176fa0.png' , " +
				"1399559749.0 , " +
				"2 , " +
				"'Doraemon'  " ;
		
		String sinchan = "Union Select "+ 
				"'4e70e978c092255ef7002923' , " +
				"'crayon-shin-chan' , " +
				"218377 , " +
				"'9e/9eee646022443abc982250d347fa7ba5f87a0b804954873c1b596ee4.jpg' , " +
				"1330502606.0 , " +
				"2 , " +
				"'Crayon Shin-chan' " ;
		
		String slamdunk = "Union Select "+ 
				"'4e70ea0fc092255ef7004a61' , " +
				"'slam-dunk' , " +
				"4752845 , " +
				"'ae/ae1cd5fb3725611e562259275dfef09b452dcec4750e322fb843eac7.jpg' , " +
				"1263765600.0 , " +
				"2 , " +
				"'Slam Dunk'  " ;
		String query = naruto + " ";
		query  += onepiece + " ";
		query  += fairyTail + " ";
		query  += bleach + " ";
		query  += conan + " ";
		query  += beelzebub + " ";
		query  += hunterxhunter + " ";
		query  += doraemon + " ";
		query  += sinchan + " ";
		query  += slamdunk;
		return query;
	}
	

}

