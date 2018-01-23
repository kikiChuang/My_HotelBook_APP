package br.liveo.hotelbook.hotelbook.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import br.liveo.hotelbook.hotelbook.BuildConfig;
import br.liveo.hotelbook.hotelbook.R;
import br.liveo.hotelbook.hotelbook.ui.BaseActivity;
import br.liveo.hotelbook.hotelbook.ui.widget.DrawShadowFrameLayout;
import br.liveo.hotelbook.hotelbook.util.UIUtils;

/**
 * Created by phx on 2016/1/27.
 */
public class AboutActivity extends BaseActivity {
    private static final String URL_TERMS = "http://m.google.com/utos";
    private static final String URL_PRIVACY_POLICY = "http://www.google.com/policies/privacy/";
    public static final String ABOUT = "br.liveo.hotelbook.hotelbook.about";


    private View rootView;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.about_terms:
                    openUrl(URL_TERMS);
                    break;
                case R.id.about_privacy_policy:
                    openUrl(URL_PRIVACY_POLICY);
                    break;
                case R.id.about_licenses:
                   // AboutUtils.showOpenSourceLicenses(AboutActivity.this);
                    break;
                case R.id.about_eula:
                    //AboutUtils.showEula(AboutActivity.this);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //重新setTitle
        this.setTitle(R.string.title_abouts);
        setContentView(R.layout.activity_about);
        rootView = findViewById(R.id.about_container);
        TextView body = (TextView) rootView.findViewById(R.id.about_main);
        body.setText(Html.fromHtml(getString(R.string.about_main, BuildConfig.VERSION_NAME)));
        rootView.findViewById(R.id.about_terms).setOnClickListener(mOnClickListener);
        rootView.findViewById(R.id.about_privacy_policy).setOnClickListener(mOnClickListener);
        rootView.findViewById(R.id.about_licenses).setOnClickListener(mOnClickListener);
        rootView.findViewById(R.id.about_eula).setOnClickListener(mOnClickListener);

        overridePendingTransition(0, 0);
    }


    @Override
    protected int getSelfNavDrawerItem() {
        return NAVDRAWER_ITEM_ABOUTUS;
}

    private void setContentTopClearance(int clearance) {
        if (rootView != null) {
            rootView.setPadding(rootView.getPaddingLeft(), clearance,
                    rootView.getPaddingRight(), rootView.getPaddingBottom());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int actionBarSize = UIUtils.calculateActionBarSize(this);
        DrawShadowFrameLayout drawShadowFrameLayout =
                (DrawShadowFrameLayout) findViewById(R.id.main_content);
        if (drawShadowFrameLayout != null) {
            drawShadowFrameLayout.setShadowTopOffset(actionBarSize);
        }
        setContentTopClearance(actionBarSize);
    }

    private void openUrl(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}
