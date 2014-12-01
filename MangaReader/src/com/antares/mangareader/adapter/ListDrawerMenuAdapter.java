package com.antares.mangareader.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.antares.jahamanga.MainActivity.MenuContent;
import com.antares.jahamanga.R;
import com.antares.mangareader.util.Style;
import com.antares.mangareader.util.Style.Font.FontType;

public class ListDrawerMenuAdapter extends BaseAdapter {

	private Activity mContext;
	private SparseArray<MenuContent> listOfMenus;
	
	public ListDrawerMenuAdapter(Activity mContext, SparseArray<MenuContent> list ) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.listOfMenus = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (listOfMenus==null)?0:listOfMenus.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return (listOfMenus==null)?0:listOfMenus.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if (convertView == null){
			holder = new Holder();
			convertView = mContext.getLayoutInflater().inflate(R.layout.list_item_text, parent, false);
			holder.text1 = (TextView)convertView.findViewById(R.id.text_1);
			convertView.setTag(holder);
		}
		else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.text1.setText(getItem(position).toString());
		Style.applyTypeFace(mContext, FontType.GothamLight, holder.text1);
		if (listOfMenus.get(position).isSelected()){
			convertView.setBackgroundColor(0xffdedede);
		}
		else{
			convertView.setBackgroundColor(Color.WHITE);
		}
		
		return convertView;
	}

	public class Holder{
		TextView text1;
	}
}

