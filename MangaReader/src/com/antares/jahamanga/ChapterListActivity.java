package com.antares.jahamanga;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.antares.mangareader.adapter.ChapterAdapter;
import com.antares.mangareader.adapter.ChapterAdapter.ChapterModel;
import com.antares.mangareader.control.MangaControl;
import com.antares.mangareader.model.ChapterModelResponse;
import com.antares.mangareader.util.AppConstant;
import com.antares.mangareader.util.Logger;
import com.bumptech.glide.Glide;
import com.srin.lab4.common.BaseActivity;

public class ChapterListActivity extends BaseActivity implements OnCreateContextMenuListener {
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String ARG_MANGA_ID = "mangaId";
	public static final String ARG_MANGA_TITLE = "mangaTitle";
	public static final String ARG_MANGA_URL = "mangaURL";
	private ListView mGridView;
	private ChapterAdapter mAdapter;
	private MangaControl mControl;
	private LinearLayout layoutLoading;
	private ProgressBar progressLoading;
	private TextView textStatus;
	private Button btnLoadAgain;
	private View header;
	private TextView tvDescription;
	private ImageView ivCover;
	private TextView tvTitle;
	private ChapterModelResponse modelResponse = null;
	private String mangaId;
	private String mangaTitle;
	private String mangaUrl;
	private ActionBar mActionBar;
	private boolean alreadyLoadFromDb = false;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_list_chapter);
		mActionBar = getActionBar();
		mGridView = (ListView) findViewById(R.id.listData);
		header = getLayoutInflater()
				.inflate(R.layout.header_list_chapter, null);
		tvTitle = (TextView) header.findViewById(R.id.tvTitle);
		tvDescription = (TextView) header.findViewById(R.id.tvDescription);
		ivCover = (ImageView) header.findViewById(R.id.ivCover);
		layoutLoading = (LinearLayout) findViewById(R.id.layoutLoading);
		progressLoading = (ProgressBar) findViewById(R.id.progressLoading);
		textStatus = (TextView) findViewById(R.id.textStatus);
		btnLoadAgain = (Button) findViewById(R.id.btnLoadAgain);

		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setBackgroundDrawable(new ColorDrawable(0xff2c3e50));

		mGridView.addHeaderView(header);

		if (mAdapter == null) {
			mAdapter = new ChapterAdapter(this);
		}
		mGridView.setOnItemClickListener(itemClickListener);
		mangaId = getIntent().getStringExtra(ARG_MANGA_ID);
		mangaTitle = getIntent().getStringExtra(ARG_MANGA_TITLE);
		mangaUrl = getIntent().getStringExtra(ARG_MANGA_URL);
		mActionBar.setTitle("" + mangaTitle);
		mGridView.setAdapter(mAdapter);
		mControl = new MangaControl(this);

		com.parse.ParseAnalytics.trackAppOpened(getIntent());

		btnLoadAgain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showLoadingLayout();
				if (mangaId != null) {
					mControl.getChapterListFromDb(mangaId);
				} else {
					emptyData();
				}
			}
		});
		registerForContextMenu(mGridView);
		mGridView.setOnCreateContextMenuListener(this);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater =  getMenuInflater();
		inflater.inflate(R.menu.chapter_list_menu, menu);


	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (mAdapter.getCount() == 0) {
			showLoadingLayout();
			if (mangaId != null) {
				mControl.getChapterListFromDb(mangaId);
				// mControl.getChapterList(mangaId);
			} else {
				emptyData();
			}
		} else {
			showGrid();
		}
	}

	public void showLoadingLayout() {
		layoutLoading.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.VISIBLE);
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_progress);
		btnLoadAgain.setVisibility(View.GONE);
		mGridView.setVisibility(View.GONE);
	}

	public void showGrid() {
		mGridView.setVisibility(View.VISIBLE);
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
		mGridView.setVisibility(View.GONE);
	}

	public void failedLoadData() {
		layoutLoading.setVisibility(View.VISIBLE);
		btnLoadAgain.setVisibility(View.VISIBLE);
		progressLoading.setVisibility(View.GONE);
		textStatus.setVisibility(View.VISIBLE);
		textStatus.setText(R.string.loading_failed);
		mGridView.setVisibility(View.GONE);
	}

	@Override
	public void afterTask(int code, Object result) {

		switch (code) {
		case MangaControl.LOAD_MANGA_DB_SUCCESS:
			alreadyLoadFromDb = true;
			Logger.log(this, "MangaControl.LOAD_MANGA_DB_SUCCESS");
			showGrid();
			modelResponse = ((ChapterModelResponse) result);
			tvTitle.setText("" + modelResponse.title);
			tvDescription.setText("" + modelResponse.description);
			mAdapter.setMangaTitle(modelResponse.title);
			mAdapter.setData(true, modelResponse.listChapterModels);
			if (modelResponse.imageURL != null){
				Glide.with(this).load(modelResponse.imageURL).animate(android.R.anim.fade_in)				
				.placeholder(R.drawable.orca_photo_placeholder_dark)
				.error(R.drawable.orca_photo_placeholder_dark)
				.into(ivCover);
			}
			else{
				Glide.with(this).load(AppConstant.URL_IMAGE + mangaUrl).animate(android.R.anim.fade_in)				
				.placeholder(R.drawable.orca_photo_placeholder_dark)
				.error(R.drawable.orca_photo_placeholder_dark)
				.into(ivCover);
			}
			
			mControl.getChapterList(mangaId);
			setProgressBarIndeterminateVisibility(true);
			break;
		case MangaControl.LOAD_MANGA_DB_EMPTY:
			Logger.log(this, "MangaControl.LOAD_MANGA_DB_EMPTY");
			alreadyLoadFromDb = false;
			mControl.getChapterList(mangaId);
			break;
		case MangaControl.LOAD_MANGA_SUCCESS:
			Logger.log(this,  "MangaControl.LOAD_MANGA_SUCCESS");
			setProgressBarIndeterminateVisibility(false);
			if (!alreadyLoadFromDb)
			{
				showGrid();
			}
			modelResponse = ((ChapterModelResponse) result);
			mControl.saveMangaHeaderToDB(modelResponse, mangaId);
			tvTitle.setText("" + modelResponse.title);
			tvDescription.setText("" + modelResponse.description);
			mAdapter.setMangaTitle(modelResponse.title);
			if (alreadyLoadFromDb){
				mAdapter.setData(true, modelResponse.listChapterModels);
			}
			else{
				mAdapter.setData(modelResponse.chapters);
			}
			Logger.log(this, "Request MangaID = "+mangaId);
			if (modelResponse.imageURL != null){
				Glide.with(this).load(modelResponse.imageURL).animate(R.anim.fade_in)
				.fitCenter()
				.placeholder(R.drawable.orca_photo_placeholder_dark)
				.error(R.drawable.orca_photo_placeholder_dark)
				.into(ivCover);
			}
			else{
				Glide.with(this).load(AppConstant.URL_IMAGE + mangaUrl).animate(R.anim.fade_in)
				.placeholder(R.drawable.orca_photo_placeholder_dark)
				.error(R.drawable.orca_photo_placeholder_dark)
				.into(ivCover);
			}
			break;
		case MangaControl.LOAD_MANGA_EMPTY:
			Logger.log(this, "LOAD_MANGA_EMPTY");
			setProgressBarIndeterminateVisibility(false);
			if (alreadyLoadFromDb == false) {
				emptyData();
			}
			break;
		case MangaControl.LOAD_MANGA_ERROR:
			Logger.log(this, "LOAD_MANGA_ERROR");
			setProgressBarIndeterminateVisibility(false);
			if (alreadyLoadFromDb == false) {
				failedLoadData();
			}
			break;
		default:
			break;
		}
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (mAdapter != null) {
				if (arg2 > 0) {
					ChapterModel model;
					if (mAdapter.isModelAlreadyParse()){
						model = mAdapter.getItem2(arg2 - 1);
					}
					else{
						model = new ChapterModel(mAdapter.getItem(arg2 - 1));
					}

					Intent intent = new Intent(ChapterListActivity.this,
							ReadPagesActivity.class);
					intent.putExtra(ReadPagesActivity.ARG_CHAPTER_ID,
							model.chapterId);
					startActivity(intent);
				}
			}
		}
	};

	@Override
	public void showDialog(String title, String message, boolean cancelable) {

	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		ChapterModel model;
		if (mAdapter.isModelAlreadyParse()){
			model = mAdapter.getItem2(info.position - 1);
		}
		else{
			model = new ChapterModel(mAdapter.getItem(info.position - 1));
		}

		switch (item.getItemId()) {
		case R.id.action_download:
			Intent i = new Intent(this, ChapterDownloadService.class);
			i.putExtra(ChapterDownloadService.CHAPTER_ID, model.chapterId);
			i.putExtra(ChapterDownloadService.CHAPTER_NUMBER, model.chapterNumber);
			i.putExtra(ChapterDownloadService.MANGA_NAME, mangaTitle);
			startService(i);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
