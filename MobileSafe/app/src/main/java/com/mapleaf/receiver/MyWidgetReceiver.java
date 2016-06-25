package com.mapleaf.receiver;

import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mapleaf.service.WidgetService;

public class MyWidgetReceiver extends AppWidgetProvider{

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        context.startService(new Intent(context, WidgetService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.stopService(new Intent(context,WidgetService.class));
    }
}
