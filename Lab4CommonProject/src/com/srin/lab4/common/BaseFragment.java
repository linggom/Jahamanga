package com.srin.lab4.common;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.srin.lab4.common.module.IViewController;

public abstract class BaseFragment extends Fragment implements IViewController {

	protected Activity mActivity;
	
	@Override
	public abstract void afterTask(int code, Object result);

	@Override
	public void showLongToast(String message) {
		// TODO Auto-generated method stub
		if (mActivity != null){
			Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void showShortToast(String message) {
		if (mActivity != null){
			Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public abstract void showDialog(String title, String message, boolean cancelable);

	@Override
	public Activity getIViewActivity(){
		return mActivity;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}
	
	
	
}


