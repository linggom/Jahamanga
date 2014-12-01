package com.srin.lab4.common.module;

import android.app.Activity;


public interface IViewController {
	public void afterTask(int code, Object result);
	public void showLongToast(String message);
	public void showShortToast(String message);
	public void showDialog(String title, String message, boolean cancelable);
	public Activity getIViewActivity();
}
