package com.antares.mangareader.fragment;

import java.io.File;

import uk.co.senab.photoview.PhotoView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.antares.jahamanga.ChapterDownloadService;
import com.antares.jahamanga.R;
import com.antares.mangareader.global.GlobalApplication;
import com.antares.mangareader.util.AppConstant;
import com.antares.mangareader.util.Logger;
import com.bumptech.glide.Glide;
import com.srin.lab4.common.BaseFragment;

public class ImagePageFragment extends BaseFragment {
	public static final String ARG_PAGE_URL = "page_url";
	public  static final String PAGE_ID = "page_id";
	private PhotoView imageData;
	private String imageUrl ;
	private String chapterId;
	private String page;
	private ProgressBar pbLoading;
	public ImagePageFragment() {
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
		View rootView = inflater.inflate(R.layout.fragment_page_reader,
				container, false);
		imageData = (PhotoView) rootView.findViewById(R.id.photoView1);
		pbLoading = (ProgressBar)rootView.findViewById(R.id.pbLoading);
		imageUrl = getArguments().getString(ARG_PAGE_URL);
		chapterId = getArguments().getString(ChapterDownloadService.CHAPTER_ID);
		page = getArguments().getString(PAGE_ID);
		if (imageUrl != null) {
			File folder =new File(AppConstant.MANGA_FOLDER, chapterId);
			File image = new File(folder, page+AppConstant.EXTENSION);
			Logger.log(this, "" + image.getPath() + " exist : " + image.exists());
			if (image.exists()){
				Glide.with(GlobalApplication.getApplication()).load(image)
				.animate(R.anim.fade_in)
				.placeholder(R.drawable.orca_photo_placeholder_dark)
				.fitCenter()
				.into(imageData);
			}
			else{
				Glide.with(GlobalApplication.getApplication()).load(AppConstant.URL_IMAGE + imageUrl)
				.animate(R.anim.fade_in)
				.placeholder(R.drawable.orca_photo_placeholder_dark)
				.fitCenter()
				.into(imageData);
			}
		}
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}


	@Override
	public void afterTask(int code, Object result) {

	}

	@Override
	public void showDialog(String title, String message, boolean cancelable) {
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			mActivity.setResult(100);
			mActivity.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
