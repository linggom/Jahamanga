package com.antares.mangareader.util;

import java.util.List;

import android.content.res.Resources;
import android.util.TypedValue;
public class Util {

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
	}

	public static String getListMangaURL(int page){
		return AppConstant.URL + AppConstant.GET_MANGA_LIST + page;
	}
	
	public static boolean isObjectValid(Object obj){
		return (obj != null);
	}
	public static boolean isListValid(List<?> data){
		if (isObjectValid(data)){
			return data.size() > 0;
		}
		return false;
	}
}
