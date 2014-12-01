package com.antares.jahamanga;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.antares.mangareader.adapter.FavouriteMangaAdapter;
import com.antares.mangareader.control.MangaControl;
import com.antares.mangareader.model.MangaDetailModelReponse;
import com.srin.lab4.common.BaseActivity;

public class SearchMangaActivity extends BaseActivity {
	public static final String ARG_MANGA_KEYWORD = "keyword";

	public static final String ARG_SECTION_NUMBER = "section_number";
	private GridView mGridView;
	private FavouriteMangaAdapter mAdapter;
	private MangaControl mControl;
	private LinearLayout layoutLoading;
	private ProgressBar progressLoading;
	private TextView textStatus;
	private Button btnLoadAgain;
	private String mKeyword = "";
	private ActionBar mActionBar;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActionBar = getActionBar();
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setBackgroundDrawable(new ColorDrawable(0xff2c3e50));

		setContentView(R.layout.fragment_list_manga);
		mGridView = (GridView)findViewById(R.id.gridData);
		layoutLoading = (LinearLayout)findViewById(R.id.layoutLoading);
		progressLoading = (ProgressBar)findViewById(R.id.progressLoading);
		textStatus = (TextView)findViewById(R.id.textStatus);
		btnLoadAgain = (Button)findViewById(R.id.btnLoadAgain);

		mControl = new MangaControl(this);

		mGridView.setOnItemClickListener(itemClickListener);
		mKeyword = getIntent().getStringExtra(ARG_MANGA_KEYWORD);
		mActionBar.setTitle(mKeyword);
		showLoadingLayout();
		com.parse.ParseAnalytics.trackAppOpened(getIntent());
		mControl.searchManga(mKeyword);

	}


	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (mAdapter != null){
				MangaDetailModelReponse item = mAdapter.getItem(arg2);
				Intent intent = new Intent(SearchMangaActivity.this, ChapterListActivity.class );
				intent.putExtra(ChapterListActivity.ARG_MANGA_ID, item.getId());
				intent.putExtra(ChapterListActivity.ARG_MANGA_TITLE, item.getTitle());
				intent.putExtra(ChapterListActivity.ARG_MANGA_URL, item.getImg());
				startActivity(intent);
			}
		}
	}; 

	
	
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return false;
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
		btnLoadAgain.setVisibility(View.GONE);
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.search_empty);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView =
				(SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setQueryHint(getResources().getText(R.string.search_hint));
		searchView.setSearchableInfo(
				searchManager.getSearchableInfo(this.getComponentName()));
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}



	@Override
	public void afterTask(int code, Object result) {

		switch (code) {

		case MangaControl.LOAD_MANGA_DB_SUCCESS:
			showGrid();
			mAdapter = new FavouriteMangaAdapter(this, (Cursor) result);
			mGridView.setAdapter(mAdapter);

			break;
		case MangaControl.LOAD_MANGA_DB_EMPTY:
			emptyData();
			break;
		default:
			break;
		}
	}

	@Override
	public void showDialog(String title, String message, boolean cancelable) {

	}


}
