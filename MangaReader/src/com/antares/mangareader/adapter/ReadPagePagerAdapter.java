package com.antares.mangareader.adapter;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.antares.jahamanga.ChapterDownloadService;
import com.antares.jahamanga.ChapterListFragment;
import com.antares.mangareader.fragment.ImagePageFragment;
import com.antares.mangareader.model.PageModel;

public class ReadPagePagerAdapter extends FragmentStatePagerAdapter {

	List<PageModel> listPage;
	String chapterId;
	String mangaId;
	String mangarUrl;
	String mangaTitle;
	
	public ReadPagePagerAdapter(FragmentManager fm, List<PageModel> listPage, String chapterId, String mangaId, String mangaUrl, String mangaTitle) {
		super(fm);
		this.listPage = listPage;
		this.chapterId = chapterId;
		this.mangaId = mangaId;
		this.mangarUrl = mangaUrl;
		this.mangaTitle = mangaTitle;
	}

	

	@Override
	public Fragment getItem(int position) {
		if (position == listPage.size()){
			ChapterListFragment mFragment = ChapterListFragment.newInstance(mangaId, mangaTitle, mangarUrl, true);
			return mFragment;
		}
		else{
			ImagePageFragment mFragment = new ImagePageFragment();
			Bundle bundle = new Bundle();
			PageModel model = listPage.get(listPage.size() - position - 1 );
			bundle.putString(ImagePageFragment.ARG_PAGE_URL, model.url);
			bundle.putString(ImagePageFragment.PAGE_ID, model.pageNumber);
			bundle.putString(ChapterDownloadService.CHAPTER_ID, chapterId);
			mFragment.setArguments(bundle);
			return mFragment;
		}

	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		if (position == listPage.size())
		{
			return mangaTitle;
		}
		else{
			return (listPage == null) ? null : "Page " + listPage.get(getCount() - position - 1 ).pageNumber;
		}
	}
	
	@Override
	public int getCount() {
		return (listPage == null) ? 0 : (listPage.size() == 0)? 0: listPage.size()+1;
	}

}
