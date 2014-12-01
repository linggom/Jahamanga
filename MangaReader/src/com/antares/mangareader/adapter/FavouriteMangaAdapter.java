package com.antares.mangareader.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antares.jahamanga.R;
import com.antares.mangareader.model.MangaDetailModelReponse;
import com.antares.mangareader.util.AppConstant;
import com.antares.mangareader.util.Style;
import com.antares.mangareader.util.Style.Font.FontType;
import com.bumptech.glide.Glide;

public class FavouriteMangaAdapter extends CursorAdapter {

	private Context mContext;
	private LayoutInflater mInflater;

	public FavouriteMangaAdapter(Context context, Cursor cursor) {
		super(context, cursor, false);
		this.mContext = context;
		this.mInflater = Style.getGlobalLayoutInflater(mContext);
	}
	
	@Override
	public MangaDetailModelReponse getItem(int position) {
		// TODO Auto-generated method stub
		Cursor cursor = getCursor();
		
		if (cursor.moveToPosition(position)) {
			MangaDetailModelReponse manga = new MangaDetailModelReponse(cursor);
			return manga;
		}
		return null;
	}


	public static class MangaItemCache {
		public ImageView cover;
		public TextView title;
	}

	@Override
	public void bindView(View convertView, Context arg1, Cursor cursor) {
		// TODO Auto-generated method stub
		MangaItemCache cache = (MangaItemCache) convertView.getTag();
		MangaDetailModelReponse manga = new MangaDetailModelReponse(cursor);
		cache.title.setText("" + manga.getTitle());
		
//		cache.cover.setImageUrl(AppConstant.URL_IMAGE + manga.getImg(), ImageCacheManager.getInstance().getImageLoader());
//		Picasso
//			.with(GlobalApplication.getApplication())
//			.load(AppConstant.URL_IMAGE + manga.getImg())
//			.into(cache.cover);
		Glide.with(mContext).load(AppConstant.URL_IMAGE + manga.getImg())
//        .placeholder(R.drawable.loading_spinner)
		
        .animate(R.anim.fade_in)
        .into(cache.cover);
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup parent) {
		// TODO Auto-generated method stub
		View convertView;
		MangaItemCache cache = new MangaItemCache();
		convertView = mInflater.inflate(R.layout.item_manga_list, parent,false);
		cache.cover = (ImageView) convertView.findViewById(R.id.ivCover);
		cache.title = (TextView) convertView.findViewById(R.id.tvTitle);
		Style.applyTypeFace(mContext, FontType.GothamLight, cache.title);
		convertView.setTag(cache);
		return convertView;
	}
}
