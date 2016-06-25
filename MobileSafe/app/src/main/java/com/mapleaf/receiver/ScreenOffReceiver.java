package com.mapleaf.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenOffReceiver extends BroadcastReceiver {
    public ScreenOffReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("suopnigle=============================================");
    }
}
