package com.mapleaf.bean;

/**
 * Created by Mapleaf on 2016/6/18.
 */
public class BlackNumInfo {
    private String BlackNum;

    public String getBlackNum() {
        return BlackNum;
    }

    public void setBlackNum(String blackNum) {
        BlackNum = blackNum;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private String mode;

    @Override
    public String toString() {
        return "BlackNumInfo{" +
                "BlackNum='" + BlackNum + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }

    public BlackNumInfo(String blackNum, String mode) {
        BlackNum = blackNum;
        this.mode = mode;
    }
}
