package br.liveo.hotelbook.hotelbook.util;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

import br.liveo.hotelbook.hotelbook.AppApplication;
import br.liveo.hotelbook.hotelbook.R;
import br.liveo.hotelbook.hotelbook.settings.Settings;

/**
 * Created by phx on 2016/2/1.
 */
public class Utils {

    private static Context mContext = AppApplication.AppContext;

    public static int getCurrentLanguage() {
        int lang = Settings.getInstance().getInt(Settings.LANGUAGE, -1);
        if (lang == -1) {
                lang = 0;
        }
        return lang;
    }

    public static void changeLanguage(Context context, int lang) {
        Locale locale;
        String locale_code = context.getResources().getStringArray(R.array.languagesValues)[lang];
        if (locale_code.contains("-")) {
             locale = new Locale(locale_code.substring(0,
                    locale_code.indexOf("-")), locale_code.substring(
                    locale_code.indexOf("-") + 1, locale_code.length()));
        } else {
             locale  = new Locale(locale_code);
        }
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
    }


}
