package com.antares.mangareader.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.antares.jahamanga.ChapterListActivity;
import com.antares.jahamanga.ChapterListActivity2;
import com.antares.jahamanga.R;
import com.antares.jahamanga.SearchMangaActivity;
import com.antares.mangareader.adapter.FavouriteMangaAdapter;
import com.antares.mangareader.control.MangaControl;
import com.antares.mangareader.model.MangaDetailModelReponse;
import com.srin.lab4.common.BaseFragment;

public class MangaListFragment extends BaseFragment {
	public static final String ARG_SECTION_NUMBER = "section_number";
	private GridView mGridView;
	private FavouriteMangaAdapter mAdapter;
	private MangaControl mControl;
	private LinearLayout layoutLoading;
	private ProgressBar progressLoading;
	private TextView textStatus;
	private Button btnLoadAgain;
	
	public MangaListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
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

		mControl = new MangaControl(this);

		mGridView.setOnItemClickListener(itemClickListener);


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
					intent.putExtra(ChapterListActivity.ARG_MANGA_URL, item.getImg());
					startActivity(intent);
				}
			}
		}
	}; 

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		showLoadingLayout();
		mControl.loadMangaFromDb();
	};


	public void showLoadingLayout(){
		layoutLoading.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.VISIBLE);;
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_progress);
		btnLoadAgain.setVisibility(View.GONE);
		mGridView.setVisibility(View.GONE);
	}
	public void showGrid(){
		mGridView.setVisibility(View.VISIBLE);
		layoutLoading.setVisibility(View.GONE);
		progressLoading.setVisibility(View.GONE);
		btnLoadAgain.setVisibility(View.GONE);
		textStatus.setVisibility(View.GONE);
	}

	public void emptyData(){
		layoutLoading.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.GONE);
		btnLoadAgain.setVisibility(View.VISIBLE);
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_empty);
		mGridView.setVisibility(View.GONE);
	}

	public void failedLoadData(){
		layoutLoading.setVisibility(View.VISIBLE);
		btnLoadAgain.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.GONE);;
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_failed);
		mGridView.setVisibility(View.GONE);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		mActivity.getMenuInflater().inflate(R.menu.main, menu);
		SearchManager searchManager = (SearchManager) mActivity.getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView =
				(SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setQueryHint(getResources().getText(R.string.search_hint));
		searchView.setSearchableInfo(
				searchManager.getSearchableInfo(mActivity.getComponentName()));
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				Intent i = new Intent(mActivity, SearchMangaActivity.class);
				i.putExtra(SearchMangaActivity.ARG_MANGA_KEYWORD, query);
				startActivity(i);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		super.onCreateOptionsMenu(menu, inflater);
	}

	
	
	@Override
	public void afterTask(int code, Object result) {

		switch (code) {
		
		case MangaControl.LOAD_MANGA_EMPTY:
			emptyData();
			break;
		case MangaControl.LOAD_MANGA_ERROR:
			failedLoadData();
			break;
		case MangaControl.LOAD_MANGA_SUCCESS:
		case MangaControl.LOAD_MANGA_DB_SUCCESS:
			showGrid();
			mAdapter = new FavouriteMangaAdapter(mActivity, (Cursor) result);
			mGridView.setAdapter(mAdapter);
			
			break;
		case MangaControl.LOAD_MANGA_DB_EMPTY:
			mControl.getMangaList();
			break;
		default:
			break;
		}
	}

	@Override
	public void showDialog(String title, String message, boolean cancelable) {

	}
}
