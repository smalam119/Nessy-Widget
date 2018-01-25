package com.thyme.smalam119.nessy.Main;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import com.thyme.smalam119.nessy.Cons.Cons;
import com.thyme.smalam119.nessy.Cons.UserSettings;
import com.thyme.smalam119.nessy.R;

/**
 * The configuration screen for the {@link MainAppWidgetProvider MainAppWidgetProvider} AppWidget.
 */
public class MainAppWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.thyme.smalam119.nessy.Main.MainAppWidgetProvider";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private RadioGroup timeIntervalRadioGroup, quotesTypeRadioGroup;
    private Button applyButton;
    private UserSettings userSettings;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public MainAppWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.main_app_widget_configure);
        userSettings = new UserSettings(this);
        applyButton = (Button) findViewById(R.id.add_button);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = MainAppWidgetConfigureActivity.this;
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                MainAppWidgetProvider.updateAppWidget(context, appWidgetManager, mAppWidgetId);
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                Log.d("update","initiated");
                finish();
            }
        });

        timeIntervalRadioGroup = (RadioGroup) findViewById(R.id.time_interval_radio_group);
        timeIntervalRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.six_hour) {
                    userSettings.setQuotesLoadInterval(Cons.QUOTES_LOAD_INTERVAL_SIX_HOUR);
                } else if(i == R.id.twelve_hour) {
                    userSettings.setQuotesLoadInterval(Cons.QUOTES_LOAD_INTERVAL_TWELVE_HOUR);
                } else {
                    userSettings.setQuotesLoadInterval(Cons.QUOTES_LOAD_INTERVAL_TWENTY_FOUR_HOUR);
                }
            }
        });

        quotesTypeRadioGroup = (RadioGroup) findViewById(R.id.quotes_type_radio_group);
        quotesTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.inspire) {
                    userSettings.setQuotesCategory(Cons.QUOTES_CATEGORY_INSPIRE);
                } else if(i == R.id.funny) {
                    userSettings.setQuotesCategory(Cons.QUOTES_CATEGORY_FUNNY);
                }
            }
        });

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

    }
}

