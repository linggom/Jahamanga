package com.antares.mangareader.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircullarImageView extends ImageView {
	
	float radius = 32f;

	public CircullarImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public CircullarImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public CircullarImageView(Context context) {
		super(context);
	}
	

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Path clipPath = new Path();
		RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
		clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
		canvas.clipPath(clipPath);
		super.onDraw(canvas);
	}



}

