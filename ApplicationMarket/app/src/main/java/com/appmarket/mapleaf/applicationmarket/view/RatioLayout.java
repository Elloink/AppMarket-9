package com.appmarket.mapleaf.applicationmarket.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.appmarket.mapleaf.applicationmarket.R;

/**
 * Created by Mapleaf on 2016/7/11.
 */
public class RatioLayout extends FrameLayout{
    private float ratio;
    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        ratio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);
        typedArray.recycle();
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY &&heightMode!=MeasureSpec.EXACTLY &&ratio>0){
            int imageWidth = widthSize - getPaddingLeft() - getPaddingRight();
            int imageHeight = (int) (imageWidth / ratio  + 0.5f);
            int height = imageHeight + getPaddingBottom() + getPaddingTop();

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
