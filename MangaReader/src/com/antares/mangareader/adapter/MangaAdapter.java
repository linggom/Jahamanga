package com.antares.mangareader.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.antares.jahamanga.R;
import com.antares.mangareader.model.MangaDetailModelReponse;
import com.antares.mangareader.util.AppConstant;
import com.antares.mangareader.util.Style;
import com.antares.mangareader.util.Style.Font.FontType;
import com.bumptech.glide.Glide;

public class MangaAdapter extends BaseAdapter {

	private Context mContext;
	private List<MangaDetailModelReponse> listOfMangas;
	private LayoutInflater mInflater;

	public MangaAdapter(Context context) {
		this.mContext = context;
		this.mInflater = Style.getGlobalLayoutInflater(mContext);
	}

	public void setData(List<MangaDetailModelReponse> listOfMangas) {
		if (this.listOfMangas == null) {
			this.listOfMangas = listOfMangas;
		} else {
			this.listOfMangas.addAll(listOfMangas);
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (this.listOfMangas == null)? 0 : listOfMangas.size();
	}

	@Override
	public MangaDetailModelReponse getItem(int arg0) {
		// TODO Auto-generated method stub
		return (listOfMangas == null) ? null : listOfMangas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MangaItemCache cache = new MangaItemCache();
		MangaDetailModelReponse manga = getItem(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_manga_list, parent,
					false);
			cache.cover = (ImageView) convertView.findViewById(R.id.ivCover);
			cache.title = (TextView) convertView.findViewById(R.id.tvTitle);
			convertView.setTag(cache);
		} else {
			cache = (MangaItemCache) convertView.getTag();
		}
		Style.applyTypeFace(mContext, FontType.RobotoLight, cache.title);
		cache.title.setText("" + manga.getTitle());
		//		cache.cover.setImageUrl(AppConstant.URL_IMAGE + manga.getImg(), ImageCacheManager.getInstance().getImageLoader());
		Glide.with(mContext).load(AppConstant.URL_IMAGE + manga.getImg())
		.animate(R.anim.fade_in)
		.into(cache.cover);

		return convertView;
	}

	public static class MangaItemCache {
		public ImageView cover;
		public TextView title;
	}
}
