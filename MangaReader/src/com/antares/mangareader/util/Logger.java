package com.antares.mangareader.util;

import android.util.Log;

public class Logger {
	public static void log(Class<?> obj, String message){
		Log.e(obj.getSimpleName(), String.valueOf(message));
	}
	
	public static void log(String tag, String message){
		Log.e(tag, String.valueOf(message));
	}
	
	public static void log(Object tag, String message){
		Log.e(tag.getClass().getSimpleName(), String.valueOf(message));
	} 
}
