package com.mapleaf.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Mapleaf on 2016/5/21.
 */

public class HomeTextView extends TextView{
    public HomeTextView(Context context) {
        super(context);
    }

    public HomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
