package com.teachableapps.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {
    private static final String TAG = BakingWidgetProvider.class.getSimpleName();

    private static String mRecipeName;

    public static void updateRecipeWidgets(Context context, AppWidgetManager appWidgetManager,
                                           String recipename, int[] appWidgetIds) {
        mRecipeName = recipename;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Set remote views
        RemoteViews rv = getRecipeGridRemoteView(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews getRecipeGridRemoteView(Context context) {
        // Construct the RemoteViews object
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        // Set Title
        rv.setTextViewText(R.id.appwidget_text, mRecipeName);

        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent widgetintent = new Intent(context, GridWidgetService.class);
        rv.setRemoteAdapter(R.id.widget_listview, widgetintent);

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Widgets allow click handlers to only launch pending intents
        rv.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        return rv;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

