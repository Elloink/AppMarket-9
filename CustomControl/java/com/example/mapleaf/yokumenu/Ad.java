package com.example.mapleaf.yokumenu;

/**
 * Created by Mapleaf on 2016/6/27.
 */
public class Ad {
    private int ImageViewId;
    private String text;

    public Ad(int imageViewId, String text) {
        ImageViewId = imageViewId;
        this.text = text;
    }

    public int getImageViewId() {
        return ImageViewId;
    }

    public void setImageViewId(int imageViewId) {
        ImageViewId = imageViewId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
