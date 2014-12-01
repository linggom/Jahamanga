package com.antares.jahamanga;

import java.util.List;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.antares.mangareader.adapter.ReadPagePagerAdapter;
import com.antares.mangareader.control.MangaControl;
import com.antares.mangareader.model.PageModel;
import com.antares.mangareader.util.Logger;
import com.srin.lab4.common.BaseActivity;

public class ReadPagesActivity extends BaseActivity {
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String ARG_CHAPTER_ID = "chapterId";
	private ViewPager pager;
	private ReadPagePagerAdapter mAdapter;
	private MangaControl mControl;
	private LinearLayout layoutLoading;
	private ProgressBar progressLoading;
	private TextView textStatus;
	private Button btnLoadAgain;
	private String chapterId;
	private ActionBar mActionBar;
	private boolean alreadyLoadFromDb;
	private String mangaId;
	private String mangaTitle;
	private String mangaUrl;

	public ReadPagesActivity() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_page);
		mActionBar = getActionBar();
		mActionBar.setBackgroundDrawable(new ColorDrawable(0xaa000000));
		pager = (ViewPager) findViewById(R.id.pager);
		layoutLoading = (LinearLayout) findViewById(R.id.layoutLoading);
		progressLoading = (ProgressBar)findViewById(R.id.progressLoading);
		textStatus = (TextView) findViewById(R.id.textStatus);
		btnLoadAgain = (Button) findViewById(R.id.btnLoadAgain);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);

		pager.setOnPageChangeListener(pageChange);
		chapterId = getIntent().getStringExtra(ARG_CHAPTER_ID);
		mangaId = getIntent().getStringExtra(ChapterListActivity.ARG_MANGA_ID);
		mangaTitle = getIntent().getStringExtra(ChapterListActivity.ARG_MANGA_TITLE);
		mangaUrl = getIntent().getStringExtra(ChapterListActivity.ARG_MANGA_URL);
		mControl = new MangaControl(this);
		com.parse.ParseAnalytics.trackAppOpened(getIntent());
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showLoadingLayout();
		if (chapterId != null){
			mControl.getPageFromDb(chapterId);
		}
		else{
			//			mControl.getPageList(null);
			emptyData();
		}
	}


	public void showLoadingLayout() {
		layoutLoading.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.VISIBLE);
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_progress);
		btnLoadAgain.setVisibility(View.GONE);
		pager.setVisibility(View.GONE);
	}

	public void showGrid() {
		pager.setVisibility(View.VISIBLE);
		layoutLoading.setVisibility(View.GONE);
		progressLoading.setVisibility(View.GONE);
		btnLoadAgain.setVisibility(View.GONE);
		textStatus.setVisibility(View.GONE);
	}

	public void emptyData() {
		layoutLoading.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.GONE);
		btnLoadAgain.setVisibility(View.VISIBLE);
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_empty);
		pager.setVisibility(View.GONE);
	}

	public void failedLoadData() {
		layoutLoading.setVisibility(View.VISIBLE);
		btnLoadAgain.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.GONE);
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_failed);
		pager.setVisibility(View.GONE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterTask(int code, Object result) {

		switch (code) {
		case MangaControl.LOAD_MANGA_DB_SUCCESS:
			showGrid();
			List<PageModel> model = (List<PageModel>) result;
			mAdapter = new ReadPagePagerAdapter(getSupportFragmentManager(), model, chapterId, mangaId, mangaUrl, mangaTitle);
			pager.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			pager.invalidate();
			setTitle("Page " + 0);
			alreadyLoadFromDb = true;
			break;
		case MangaControl.LOAD_MANGA_DB_EMPTY:
			Logger.log(this, "MangaControl.LOAD_MANGA_DB_EMPTY");	
			alreadyLoadFromDb = false;
			mControl.getPageList(chapterId);
		case MangaControl.LOAD_MANGA_SUCCESS:
			showGrid();
			model = PageModel.createFromList((List<List<String>>) result);
			mControl.savePageToDb(chapterId, model);
			mAdapter = new ReadPagePagerAdapter(getSupportFragmentManager(), model, chapterId, mangaId, mangaUrl, mangaTitle);
			pager.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			pager.invalidate();
			setTitle("Page " + 0);
			break;
		case MangaControl.LOAD_MANGA_EMPTY:
			if (!alreadyLoadFromDb){
				emptyData();
			}
			break;
		case MangaControl.LOAD_MANGA_ERROR:
			if (!alreadyLoadFromDb){
				failedLoadData();
			}
			break;
		default:
			break;
		}
	}

	SimpleOnPageChangeListener pageChange = new SimpleOnPageChangeListener(){
		public void onPageSelected(int position) {
			if (mAdapter != null){
				mActionBar.setTitle("" + mAdapter.getPageTitle(position) );
			}
		};
	};

	@Override
	public void showDialog(String title, String message, boolean cancelable) {

	}

}
