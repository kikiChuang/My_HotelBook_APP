package br.liveo.hotelbook.hotelbook;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.security.ProviderInstaller;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import br.liveo.hotelbook.hotelbook.util.Utils;

import static br.liveo.hotelbook.hotelbook.util.LogUtils.LOGE;
import static br.liveo.hotelbook.hotelbook.util.LogUtils.LOGW;
import static br.liveo.hotelbook.hotelbook.util.LogUtils.makeLogTag;
/**
 * Created by phx on 2016/1/28.
 */
public class AppApplication extends Application {

    private static final String TAG = makeLogTag(AppApplication.class);
    public static Context AppContext = null;
    private int mLang = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = getApplicationContext();

        mLang = Utils.getCurrentLanguage();
        if (mLang > -1) {
            Utils.changeLanguage(this, mLang);
        }

        // Ensure an updated security provider is installed into the system when a new one is
        // available via Google Play services.
        try {
            ProviderInstaller.installIfNeededAsync(getApplicationContext(),
                    new ProviderInstaller.ProviderInstallListener() {
                        @Override
                        public void onProviderInstalled() {
                            LOGW(TAG, "New security provider installed.");
                        }

                        @Override
                        public void onProviderInstallFailed(int errorCode, Intent intent) {
                            LOGE(TAG, "New security provider install failed.");
                            // No notification shown there is no user intervention needed.
                        }
                    });
        } catch (Exception ignorable) {
            LOGE(TAG, "Unknown issue trying to install a new security provider.", ignorable);
        }

        initImageLoader(getApplicationContext());
    }


    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

}
