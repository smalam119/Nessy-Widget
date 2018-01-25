package com.thyme.smalam119.nessy.Cons;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by smalam119 on 1/15/18.
 */

public class UserSettings {

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public UserSettings(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(Cons.SHARED_PREF_NAME,0);
        mEditor = mSharedPreferences.edit();
    }

    public int getQuotesLoadInterval() {
        return mSharedPreferences.getInt("QUOTES_LOAD_INTERVAL",Cons.QUOTES_LOAD_INTERVAL);
    }

    public  void setQuotesLoadInterval(int quotesLoadInterval) {
        mEditor.putInt("QUOTES_LOAD_INTERVAL",quotesLoadInterval);
        mEditor.commit();
    }

    public String getQuotesCategory() {
        return mSharedPreferences.getString("QUOTES_CATEGORY_FUNNY",Cons.QUOTES_CATEGORY_FUNNY);
    }

    public void setQuotesCategory(String quotesCategory) {
        mEditor.putString("QUOTES_CATEGORY_FUNNY",quotesCategory);
        mEditor.commit();
    }

}
