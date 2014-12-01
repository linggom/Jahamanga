package com.antares.mangareader.adapter;

import java.util.Locale;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.antares.mangareader.fragment.FavouriteMangaListFragment;
import com.antares.mangareader.fragment.MangaListFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	

	@Override
	public Fragment getItem(int position) {
		Fragment mFragment = null;
		if (position == 1){
			mFragment = new MangaListFragment();
		}
		else if (position == 0){
			mFragment = new FavouriteMangaListFragment();
		}
//		else if (position == 2){
//			mFragment = new ChapterListFragment();
//		}
		return mFragment;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return "1 a".toUpperCase(l);
		case 1:
			return "1 b".toUpperCase(l);
		case 2:
			return "1 C".toUpperCase(l);
		}
		return null;
	}
}
