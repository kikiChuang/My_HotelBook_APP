package br.liveo.hotelbook.hotelbook.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//import br.liveo.hotelbook.hotelbook.Config;
//import br.liveo.hotelbook.hotelbook.util.TimeUtils;
//import br.liveo.hotelbook.hotelbook.UIUtils;
//import br.liveo.hotelbook.hotelbook.welcome.WelcomeActivity;

import java.util.HashMap;
import java.util.TimeZone;

import static br.liveo.hotelbook.hotelbook.util.LogUtils.makeLogTag;

/**
 * Utilities and constants related to app settings_prefs.
 */
public class SettingsUtils {

    private static final String TAG = makeLogTag(SettingsUtils.class);

    /**
     * This is changed each year to effectively reset certain preferences that should be re-asked
     * each year. Note, res/xml/settings_prefs.xml must be updated when this value is updated.
     */
    private static final String CONFERENCE_YEAR_PREF_POSTFIX = "_2016";


    /**
     * Boolean indicating whether the app has performed the (one-time) welcome flow.
     */
    public static final String PREF_WELCOME_DONE = "pref_welcome_done" +
            CONFERENCE_YEAR_PREF_POSTFIX;

    /**
     * Boolean preference indicating that the user will be attending the conference.
     */
    public static final String PREF_ATTENDEE_AT_VENUE = "pref_attendee_at_venue" +
            CONFERENCE_YEAR_PREF_POSTFIX;


    /**
     * String storing the language code interval that's currently configured.
     */
    public static final String PREF_LANGUAGE_SETTING_CODE="pref_language";


    /**
     * Return true if the first-app-run-activities have already been executed.
     *
     * @param context Context to be used to lookup the {@link android.content.SharedPreferences}.
     */
    public static boolean isFirstRunProcessComplete(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_WELCOME_DONE, false);
    }

    /**
     * Mark {@code newValue whether} this is the first time the first-app-run-processes have run.
     * Managed by {@link br.liveo.hotelbook.hotelbook.ui.BaseActivity the}
     * {@link} base activities.
     *
     * @param context  Context to be used to edit the {@link android.content.SharedPreferences}.
     * @param newValue New value that will be set.
     */
    public static void markFirstRunProcessesDone(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_WELCOME_DONE, newValue).apply();
    }

    /**
     * Mark that a locale settings (stores current locale as 'last locale code' preference).
     *
     * @param context Context to be used to edit the {@link android.content.SharedPreferences}.
     */
    public static void markLocaleCode(final Context context, String newValue){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(PREF_LANGUAGE_SETTING_CODE, newValue).apply();
    }

    /**
     * Get that a locale settings string(stores current locale as 'last locale code' preference).
     *
     * @param context Context to be used to edit the {@link android.content.SharedPreferences}.
     */
    public static String getLocaleCode(final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_LANGUAGE_SETTING_CODE, "");
    }


    /**
     * Helper method to register a settings_prefs listener. This method does not automatically handle
     * {@code unregisterOnSharedPreferenceChangeListener() un-registering} the listener at the end
     * of the {@code context} lifecycle.(儲存所有監聽setting item 的設定並寫到 Preference)
     *
     * @param context  Context to be used to lookup the {@link android.content.SharedPreferences}.
     * @param listener Listener to register.
     */
    public static void registerOnSharedPreferenceChangeListener(final Context context,
                                                                SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Helper method to un-register a settings_prefs listener typically registered with
     * {@code registerOnSharedPreferenceChangeListener()}
     *
     * @param context  Context to be used to lookup the {@link android.content.SharedPreferences}.
     * @param listener Listener to un-register.
     */
    public static void unregisterOnSharedPreferenceChangeListener(final Context context,
                                                                  SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
