package br.liveo.hotelbook.hotelbook.settings;

import android.content.Context;
import android.content.SharedPreferences;

import br.liveo.hotelbook.hotelbook.AppApplication;

/**
 * Created by phx on 2016/2/1.
 */
public class Settings {
    public static final String XML_NAME = "settings";
    public static final String LANGUAGE = "language";
    private static Settings sInstance;
    private SharedPreferences mPrefs;

    public static Settings getInstance() {
        if (sInstance == null) {
            sInstance = new Settings(AppApplication.AppContext);
        }
        return sInstance;
    }

    private Settings(Context context) {
        mPrefs = context.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);
    }

    public Settings putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).commit();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mPrefs.getInt(key, defValue);
    }
}