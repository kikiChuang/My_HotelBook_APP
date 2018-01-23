package br.liveo.hotelbook.hotelbook.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import br.liveo.hotelbook.hotelbook.about.AboutActivity;
import br.liveo.hotelbook.hotelbook.util.LogUtils;

/**
 * Created by phx on 2016/1/29.
 */
public class LanguageService extends IntentService {

    private static final String TAG = LogUtils.makeLogTag(LanguageService.class);

    public static final String ACTION_NOTIFY_SESSION =
            "br.liveo.hotelbook.hotelbook.action.Language";


    /**
     * Creates a DataBootstrapService.
     */
    public LanguageService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Test", "ddsdfdsfs");
                Intent refresh = new Intent(this, AboutActivity.class);
                startActivity(refresh);
    }
}
