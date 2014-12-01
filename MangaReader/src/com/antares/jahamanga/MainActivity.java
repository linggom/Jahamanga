package com.antares.jahamanga;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.antares.mangareader.adapter.ListDrawerMenuAdapter;
import com.antares.mangareader.adapter.SectionsPagerAdapter;
import com.crashlytics.android.Crashlytics;
import com.srin.lab4.common.BaseActivity;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity {

	protected SectionsPagerAdapter mSectionsPagerAdapter;
	protected DrawerLayout mDrawerLayout;
	protected ListView mDrawerList;
	protected ActionBarDrawerToggle mDrawerToggle;
//	protected ActionBar mActionBar;
	protected SparseArray<MenuContent> menu = new SparseArray<MainActivity.MenuContent>();
	protected ListDrawerMenuAdapter mAdapter;
	protected ViewPager mViewPager;



	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
//		mActionBar = getActionBar();
//		mActionBar.setBackgroundDrawable(new ColorDrawable(0xff2c3e50));
		setContentView(R.layout.activity_main);
        Fabric.with(this, new Crashlytics());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//		mViewPager.setTransitionEffect(TransitionEffect.Stack);
		menu.put(0, new MenuContent("Recomended Manga", false));
		menu.put(1, new MenuContent("All Manga List", true));
//		menu.put(2, new MenuContent("Recent Manga", false));

		mAdapter = new ListDrawerMenuAdapter(this, menu);

		mViewPager.setAdapter(mSectionsPagerAdapter);
		loadNavigationDrawer();
		mViewPager.setOnPageChangeListener(mPageChangeListener);
		setTitle(menu.get(0).toString());
		mViewPager.setCurrentItem(0);

	}

	SimpleOnPageChangeListener mPageChangeListener = new SimpleOnPageChangeListener() {
		public void onPageSelected(int position) {
			for (int i = 0; i < menu.size(); i++) {
				
				if (position != i) {
					menu.put(i, menu.get(i).changeStatus(false));
				} else {
					menu.put(i, menu.get(i).changeStatus(true));
				}

			}

			setTitle("" + menu.get(position));
			mDrawerList.setSelection(position);

			mAdapter.notifyDataSetChanged();
			mDrawerList.invalidate();

		};
	};

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
    }

	@Override
	public void afterTask(int code, Object result) {
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
				mDrawerLayout.closeDrawer(Gravity.LEFT);
			} else {
				mDrawerLayout.openDrawer(Gravity.LEFT);
			}

			break;
		case R.id.action_settings:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("About Manga Reader");
			builder.setMessage("Powered By Koteka Labs & mangaeden.com");
			builder.show();
			break;

		
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);

	}

	@Override
	public void showDialog(String title, String message, boolean cancelable) {
		// TODO Auto-generated method stub
	}

	public class MenuContent {
		private String menu;
		private boolean isSelected;

		public MenuContent(String menu, boolean value) {
			// TODO Auto-generated constructor stub
			setMenu(menu);
			setSelected(value);
		}

		public String getMenu() {
			return menu;
		}

		public void setMenu(String menu) {
			this.menu = menu;
		}

		public boolean isSelected() {
			return isSelected;
		}

		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}

		public MenuContent changeStatus(boolean new_stat) {
			setSelected(new_stat);
			return this;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return menu;
		}

	}
	

	protected void loadNavigationDrawer() {
//		mActionBar.setHomeButtonEnabled(true);
//		mActionBar.setDisplayHomeAsUpEnabled(true);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		mDrawerList.setAdapter(mAdapter);
		mDrawerList.setOnItemClickListener(mItemClickListener);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		//
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerList.setSelection(0);

	}

	OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (arg2 >= 0 && arg2 <= 2) {
				mDrawerList.setSelection(arg2);
				mViewPager.setCurrentItem(arg2, true);
				for (int i = 0; i < menu.size(); i++) {
					if (arg2 != i) {
						menu.put(i, menu.get(i).changeStatus(false));
					} else {
						menu.put(i, menu.get(i).changeStatus(true));
					}

				}

				mAdapter.notifyDataSetChanged();
				mDrawerList.invalidate();
			} 
			mDrawerLayout.closeDrawers();

		}
	};

}
