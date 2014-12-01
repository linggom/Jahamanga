package com.antares.jahamanga;

 
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class ChapterListActivity2 extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_list_chapter_2);
		String mangaId = getIntent().getStringExtra(ChapterListFragment.ARG_MANGA_ID);
		String mangaTitle = getIntent().getStringExtra(ChapterListFragment.ARG_MANGA_TITLE);
		String mangaUrl = getIntent().getStringExtra(ChapterListFragment.ARG_MANGA_URL);
		getSupportFragmentManager().beginTransaction().replace(R.id.frame_chapter_list, ChapterListFragment.newInstance(mangaId, mangaTitle, mangaUrl)).commit();
		
	}
	
	@Override
	protected void onActivityResult(int arg0, int resultCode, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, resultCode, arg2);
		if (resultCode == ChapterListFragment.CODE_BACK_TO_HOME){
			finish();
		}
	}
}