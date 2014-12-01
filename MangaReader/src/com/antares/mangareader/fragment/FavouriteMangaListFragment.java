package com.antares.mangareader.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.antares.jahamanga.ChapterListActivity;
import com.antares.jahamanga.ChapterListActivity2;
import com.antares.jahamanga.R;
import com.antares.jahamanga.provider.DbMangaReader;
import com.antares.mangareader.adapter.FavouriteMangaAdapter;
import com.antares.mangareader.control.MangaControl;
import com.antares.mangareader.global.GlobalApplication;
import com.antares.mangareader.model.MangaDetailModelReponse;
import com.srin.lab4.common.BaseFragment;

public class FavouriteMangaListFragment extends BaseFragment {
	public static final String ARG_SECTION_NUMBER = "section_number";
	private GridView mGridView;
	private FavouriteMangaAdapter mAdapter;
	private LinearLayout layoutLoading;
	private ProgressBar progressLoading;
	private TextView textStatus;
	private Button btnLoadAgain;
	boolean isLoading = false;

	public FavouriteMangaListFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list_manga,
				container, false);
		mGridView = (GridView)rootView.findViewById(R.id.gridData);
		layoutLoading = (LinearLayout)rootView.findViewById(R.id.layoutLoading);
		progressLoading = (ProgressBar)rootView.findViewById(R.id.progressLoading);
		textStatus = (TextView)rootView.findViewById(R.id.textStatus);
		btnLoadAgain = (Button)rootView.findViewById(R.id.btnLoadAgain);

		

		mGridView.setOnItemClickListener(itemClickListener);
		btnLoadAgain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isLoading == false){
					showLoadingLayout();
					loadData();
				}
			}
		});

		return rootView;
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (mActivity != null){
				if (mAdapter != null){
					MangaDetailModelReponse item = mAdapter.getItem(arg2);
					Intent intent = new Intent(mActivity, ChapterListActivity2.class );
					intent.putExtra(ChapterListActivity.ARG_MANGA_ID, item.getId());
					intent.putExtra(ChapterListActivity.ARG_MANGA_TITLE, item.getTitle());
					startActivity(intent);
				}
			}
		}
	};

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (mAdapter == null ){
			showLoadingLayout();
			loadData();
		}
		else{
			showGrid();
		}
	};
	public void showLoadingLayout(){
		isLoading = true;
		layoutLoading.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.VISIBLE);;
		textStatus.setVisibility(View.VISIBLE);
		btnLoadAgain.setVisibility(View.GONE);
		textStatus.setText(R.string.loading_progress);
		mGridView.setVisibility(View.GONE);
	}

	public void showGrid(){
		isLoading = false;
		btnLoadAgain.setVisibility(View.GONE);
		mGridView.setVisibility(View.VISIBLE);
		layoutLoading.setVisibility(View.GONE);
		progressLoading.setVisibility(View.GONE);;
		textStatus.setVisibility(View.GONE);
	}

	public void emptyData(){
		isLoading = false;
		btnLoadAgain.setVisibility(View.VISIBLE);
		layoutLoading.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.GONE);;
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_empty);
		mGridView.setVisibility(View.GONE);
	}
	public void failedLoadData(){
		isLoading = false;
		btnLoadAgain.setVisibility(View.VISIBLE);
		layoutLoading.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.GONE);;
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_failed);
		mGridView.setVisibility(View.GONE);
	}

	
	public void loadData(){
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Cursor cursor = GlobalApplication
						.getApplication()
						.getContentResolver()
						.query(DbMangaReader.Tb_Favourite_Manga.URI, null, null, null, null);
				if (cursor != null ){
					if (cursor.moveToFirst()){
						showGrid();
						mAdapter = new FavouriteMangaAdapter(mActivity, cursor);
						mGridView.setAdapter(mAdapter);
					}
					else{
						failedLoadData();
					}	
				}
				else{
					failedLoadData();
				}
				
			}
		});
	}

	@Override
	public void afterTask(int code, Object result) {

		switch (code) {
		case MangaControl.LOAD_MANGA_SUCCESS:
			showGrid();
			break;
		case MangaControl.LOAD_MANGA_EMPTY:
			emptyData();
			break;
		case MangaControl.LOAD_MANGA_ERROR:
			failedLoadData();
			break;
		default:
			break;
		}
	}

	@Override
	public void showDialog(String title, String message, boolean cancelable) {

	}
}
