package com.srin.lab4.common.controller;

import com.srin.lab4.common.module.IViewController;

public abstract class BaseController {
	protected IViewController mIView;
	
	public BaseController(IViewController mIView) {
		this.setController(mIView);
	}

	public IViewController getController() {
		return mIView;
	}

	public void setController(IViewController mController) {
		this.mIView = mController;
	}
}
