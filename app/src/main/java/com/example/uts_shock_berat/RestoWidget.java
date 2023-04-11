package com.example.uts_shock_berat;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RestoWidget extends AppWidgetProvider {

    public static final String ACTION_UPDATE_CLICK = "com.example.uts_shock_berat.ACTION_UPDATE_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.resto_widget);
            String latestResto = MainActivity.getLatestResto();

            // set textview and click listener on the button
            views.setTextViewText(R.id.textview_widget, latestResto);
            views.setOnClickPendingIntent(R.id.button_update, getUpdateIntent(context, appWidgetId));

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private PendingIntent getUpdateIntent(Context context, int appWidgetId) {
        Intent intent = new Intent(context, RestoWidget.class);
        intent.setAction(ACTION_UPDATE_CLICK);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // Add the FLAG_IMMUTABLE flag to the PendingIntent
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_UPDATE_CLICK.equals(intent.getAction())) {
            // Update the text view with a new random value
            String latestResto = MainActivity.getLatestResto();
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.resto_widget);
            views.setTextViewText(R.id.textview_widget, latestResto);
            ComponentName componentName = new ComponentName(context, RestoWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, views);
        }
    }


}
