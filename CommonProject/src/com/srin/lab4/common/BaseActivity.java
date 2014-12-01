package com.srin.lab4.common;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.srin.lab4.common.module.IViewController;

public abstract class BaseActivity extends ActionBarActivity implements IViewController {

	
	@Override
	public abstract void afterTask(int code, Object result);

	@Override
	public void showLongToast(String message) {
		// TODO Auto-generated method stub
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void showShortToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public abstract void showDialog(String title, String message, boolean cancelable);

	@Override
	public Activity getIViewActivity(){
		return this;
	}
	
}


