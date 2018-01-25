package com.thyme.smalam119.nessy.Service;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import com.thyme.smalam119.nessy.Cons.Cons;
import com.thyme.smalam119.nessy.Cons.LocalQuotesProvider;
import com.thyme.smalam119.nessy.Cons.UserSettings;
import com.thyme.smalam119.nessy.Main.MainAppWidgetConfigureActivity;
import com.thyme.smalam119.nessy.Main.MainAppWidgetProvider;
import com.thyme.smalam119.nessy.Model.Quote;
import com.thyme.smalam119.nessy.Model.QuotesResponse;
import com.thyme.smalam119.nessy.Network.ApiClient;
import com.thyme.smalam119.nessy.Network.ApiInterface;
import com.thyme.smalam119.nessy.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by smalam119 on 1/10/18.
 */

public class UpdateWidgetService extends Service {
    private String LOG_TAG = UpdateWidgetService.class.getSimpleName();
    private UserSettings mUserSettings;
    private RemoteViews remoteView;
    private LocalQuotesProvider mLocalQuotesProvider;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mUserSettings = new UserSettings(this);
        remoteView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.main_app_widget);
        mLocalQuotesProvider = new LocalQuotesProvider();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //java.lang.NullPointerException: Attempt to invoke virtual method
        //'int[] android.content.Intent.getIntArrayExtra(java.lang.String)' on a null object reference


        try {
            int[] allWidgetIds = intent
                    .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

            if(allWidgetIds != null) {
                for(int widgetId : allWidgetIds) {
                    try {
                        requestQuote(widgetId);
                    } catch(Throwable e) {
                        setLocalQuote(widgetId,remoteView);
                    }
                    Log.d("onStartCommand","called " + startId + " " + allWidgetIds.length);
                }
            }
        } catch (Exception e) {
            //do nothing
        }



        return super.onStartCommand(intent, flags, startId);
    }


    private void setOnClickOnTextView(int[] allWidgetIds, RemoteViews view) {
        Intent clickIntent = new Intent(this.getApplicationContext(),
                MainAppWidgetProvider.class);

        clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                allWidgetIds);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 0, clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.quotes_text_view, pendingIntent);
    }

    private void setOnClickGearButton(RemoteViews view) {
        Intent intent = new Intent(this.getApplicationContext(),
                MainAppWidgetConfigureActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.setting_button, pendingIntent);
    }

    private void updateView(int widgetId, Quote displayableQuotesLocal, RemoteViews remoteView) {
        remoteView.setTextViewText(R.id.quotes_text_view, displayableQuotesLocal.getQuote());
        remoteView.setTextViewText(R.id.author_text_view, displayableQuotesLocal.getAuthor());
        remoteView.setImageViewResource(R.id.cover_image_view,mLocalQuotesProvider.getRandomImage());
        remoteView.setTextViewText(R.id.date_text_view, Cons.getCurrentDate("dd MMMM yyyy"));
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        appWidgetManager.updateAppWidget(widgetId, remoteView);
    }

    private void setLocalQuote(int widgetId, RemoteViews remoteView) {
        Quote quote = mLocalQuotesProvider.getRandomQuote();
        updateView(widgetId,quote,remoteView);
        Log.d("quotes","from local: " + quote.getQuote() + " " + quote.getAuthor());
    }

    private void requestQuote(final int widgetId) {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call call = apiService.getQuote(mUserSettings.getQuotesCategory());
        call.enqueue(new Callback<QuotesResponse>() {
            @Override
            public void onResponse(Call<QuotesResponse> call, Response<QuotesResponse> response) {
                if(response.code() == 200) {
                    String quote = response.body().getContents().getQuotes()[0].getQuote();
                    String author = response.body().getContents().getQuotes()[0].getAuthor();
                    Quote quotes = new Quote(quote,author);
                    updateView(widgetId,quotes,remoteView);
                    Log.d("quotes","from remote: " + response.body().getContents().getQuotes()[0].getQuote());
                } else {
                    setLocalQuote(widgetId,remoteView);
                }
            }

            @Override
            public void onFailure(Call<QuotesResponse> call, Throwable t) {
                Log.d(LOG_TAG,t.getMessage());
                setLocalQuote(widgetId,remoteView);
            }
        });
    }

}
