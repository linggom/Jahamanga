package com.antares.mangareader.util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Style {

	public static LayoutInflater getGlobalLayoutInflater(Context context){
		return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public static void applyTypeFace(Context context, int FontType, View ... view){
		if (FontType < 0 || FontType > 11 )return;
		else{
			Typeface tf = Style.Font.getFont(context, FontType);
			for (View view2 : view) {
				if (view != null){
					if (view2 instanceof TextView){
						((TextView) view2).setTypeface(tf);
					}
					else if (view2 instanceof Button){
						((Button) view2).setTypeface(tf);
					}
					else if (view2 instanceof EditText ){
						((EditText) view2).setTypeface(tf);
					}
				}
			}
		}
	}
	
	


	public static final class Font{

		public static final class FontType{
			private static final String [] AssetLocation = new String[]{
				"fonts/Roboto-Light.ttf",
				"fonts/Roboto-Black.ttf",
				"fonts/Roboto-Bold.ttf", 
				"fonts/Roboto-Italic.ttf",
				"fonts/Roboto-Medium.ttf",
				"fonts/Roboto-Regular.ttf",
				"fonts/Lucida-Grande.ttf",
				"fonts/HelveticaNeue.ttf",
				"fonts/Montserrat-Regular.ttf",
				"fonts/Fenix-Regular.ttf",
				"fonts/Gotham-Light.otf",
				"fonts/Gotham-Medium.otf"

			};

			public static final int RobotoLight = 0;
			public static final int RobotoBlack = 1;
			public static final int RobotoBold = 2;
			public static final int RobotoItalic = 3;
			public static final int RobotoMedium = 4;
			public static final int RobotoRegular = 5;
			public static final int LucidaGrande = 6;
			public static final int HelveticaNeue = 7;
			public static final int MontserratRegular = 8;
			public static final int FenixRegular = 9;
			public static final int GothamLight = 10;
			public static final int GothamMedium = 11;

			

		}



		public static Typeface getFont(Context context, int typeFont){

			if (typeFont > 0 && typeFont < FontType.AssetLocation.length ){
				return Typeface.createFromAsset(context.getAssets(), FontType.AssetLocation[typeFont] );
			}				
			else{
				return Typeface.createFromAsset(context.getAssets(), FontType.AssetLocation[0] );
			}

		}

	}

}

