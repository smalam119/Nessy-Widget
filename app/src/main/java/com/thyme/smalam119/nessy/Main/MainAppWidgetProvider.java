package com.thyme.smalam119.nessy.Main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import com.thyme.smalam119.nessy.Cons.Cons;
import com.thyme.smalam119.nessy.Cons.LocalQuotesProvider;
import com.thyme.smalam119.nessy.Cons.UserSettings;
import com.thyme.smalam119.nessy.Model.Quote;
import com.thyme.smalam119.nessy.R;
import com.thyme.smalam119.nessy.Service.UpdateWidgetService;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link MainAppWidgetConfigureActivity MainAppWidgetConfigureActivity}
 */
public class MainAppWidgetProvider extends AppWidgetProvider {
    private PendingIntent pendingIntent;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        CharSequence widgetText = MainAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main_app_widget);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        startWidget(context,appWidgetManager);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        UserSettings userSettings = new UserSettings(context);
        ComponentName thisWidget = new ComponentName(context,
                MainAppWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, UpdateWidgetService.class);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), userSettings.getQuotesLoadInterval(), pendingIntent);
        Log.d("onUpdate","called" + userSettings.getQuotesCategory() + " " + userSettings.getQuotesLoadInterval());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            MainAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
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

    public static void startWidget(Context context, AppWidgetManager appWidgetManager) {
        UserSettings userSettings = new UserSettings(context);
        PendingIntent pendingIntent = null;
        ComponentName thisWidget = new ComponentName(context,
                MainAppWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, UpdateWidgetService.class);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), userSettings.getQuotesLoadInterval(), pendingIntent);
        Log.d("onUpdate","called" + userSettings.getQuotesCategory() + " " + userSettings.getQuotesLoadInterval());
        //set quote content for the first time
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.main_app_widget);
        for(int widgetId : allWidgetIds) {
            updateView(widgetId,remoteView,context);
        }
    }

    public static void updateView(int widgetId, RemoteViews remoteView, Context context) {
        LocalQuotesProvider localQuotesProvider = new LocalQuotesProvider();
        Quote quote = localQuotesProvider.getRandomQuote();
        remoteView.setTextViewText(R.id.quotes_text_view, quote.getQuote());
        remoteView.setTextViewText(R.id.author_text_view, quote.getAuthor());
        remoteView.setImageViewResource(R.id.cover_image_view,localQuotesProvider.getRandomImage());
        remoteView.setTextViewText(R.id.date_text_view, Cons.getCurrentDate("dd MMMM yyyy"));
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(widgetId, remoteView);
    }
}

