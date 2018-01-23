package br.liveo.hotelbook.hotelbook.settings;

import android.content.DialogInterface;
import android.os.Bundle;

import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;


import br.liveo.hotelbook.hotelbook.R;
import br.liveo.hotelbook.hotelbook.ui.BaseActivity;
import br.liveo.hotelbook.hotelbook.ui.widget.DrawShadowFrameLayout;
import br.liveo.hotelbook.hotelbook.util.UIUtils;
import br.liveo.hotelbook.hotelbook.util.Utils;


import static br.liveo.hotelbook.hotelbook.util.LogUtils.LOGD;

/**
 * Created by phx on 2016/1/28.
 */
public class SettingsActivity extends BaseActivity{

    public static final String SETTING = "br.liveo.hotelbook.hotelbook.settings";
    private int mLang = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Language
        mLang = Utils.getCurrentLanguage();
        if (mLang > -1) {
            Utils.changeLanguage(this, mLang);
        }
        //重新setTitle
        this.setTitle(R.string.title_settings);
        setContentView(R.layout.settings_act);
        overridePendingTransition(0, 0);

    }

    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_SETTINGS;
    }

    @Override
    protected void onActionBarAutoShowOrHide(boolean shown) {
        super.onActionBarAutoShowOrHide(shown);
        DrawShadowFrameLayout frame = (DrawShadowFrameLayout) findViewById(R.id.main_content);
        frame.setShadowVisible(shown, shown);
    }

    /**
     * The Fragment is added via the R.layout.settings_act layout xml.
     */
    public static class SettingsFragment extends PreferenceFragment implements  Preference.OnPreferenceClickListener {
        private Settings mSettings;
        private Preference mLanguage;

        public SettingsFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_prefs);

            mSettings = Settings.getInstance();
            mLanguage = findPreference(Settings.LANGUAGE);
            mLanguage.setSummary(this.getResources().getStringArray(R.array.languages)[Utils.getCurrentLanguage()]);
            mLanguage.setOnPreferenceClickListener(this);

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            //SettingsUtils.unregisterOnSharedPreferenceChangeListener(getActivity(), this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
//            if (preference == mLanguage) {
                showLangDialog();
//            }
            return false;
        }


        @Override
        public void onResume() {
            super.onResume();

            // configure the fragment's top clearance to take our overlaid controls (Action Bar
            // and spinner box) into account.
            int actionBarSize = UIUtils.calculateActionBarSize(getActivity());
            DrawShadowFrameLayout drawShadowFrameLayout =
                    (DrawShadowFrameLayout) getActivity().findViewById(R.id.main_content);
            if (drawShadowFrameLayout != null) {
                drawShadowFrameLayout.setShadowTopOffset(actionBarSize);
            }

            setContentTopClearance(actionBarSize);
        }


        private void setContentTopClearance(int clearance) {
            if (getView() != null) {
                getView().setPadding(getView().getPaddingLeft(), clearance,
                        getView().getPaddingRight(), getView().getPaddingBottom());
            }
        }

        private void showLangDialog() {
            new AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.text_language))
                    .setSingleChoiceItems(
                            getResources().getStringArray(R.array.languages), Utils.getCurrentLanguage(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which != Utils.getCurrentLanguage()) {
                                        mSettings.putInt(Settings.LANGUAGE, which);
                                    }
                                    dialog.dismiss();
                                    getActivity().recreate();
                                }
                            }
                    ).show();
        }

    }
}
