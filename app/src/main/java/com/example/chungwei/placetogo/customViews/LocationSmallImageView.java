package com.example.chungwei.placetogo.customViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class LocationSmallImageView extends android.support.v7.widget.AppCompatImageView {
    public LocationSmallImageView(Context context) {
        super(context);
    }

    public LocationSmallImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LocationSmallImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width - 400);
    }

}
