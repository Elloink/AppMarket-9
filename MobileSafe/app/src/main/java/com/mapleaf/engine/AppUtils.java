package com.mapleaf.engine;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by Mapleaf on 2016/6/20.
 */
public class AppUtils {
    public static long getAvailROM(){
        File dataDirectory = Environment.getDataDirectory();
        StatFs statFs = new StatFs(dataDirectory.getPath());
        int blockSize = statFs.getBlockSize();
        int blockCount = statFs.getBlockCount();
        int availableBlocks = statFs.getAvailableBlocks();
        return availableBlocks*blockSize;
    }
}
