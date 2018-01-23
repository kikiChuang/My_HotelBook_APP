package br.liveo.hotelbook.hotelbook.test;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;

import br.liveo.hotelbook.hotelbook.R;
import br.liveo.hotelbook.hotelbook.city.CityActivity;
import br.liveo.hotelbook.hotelbook.home.HomeActivity;
import br.liveo.hotelbook.hotelbook.ui.widget.AlphaForeGroundColorSpan;
import br.liveo.hotelbook.hotelbook.ui.widget.ScrollViewHelper;

public class TestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    /** Alpha Toolbar **/
    private AlphaForeGroundColorSpan mAlphaForegroundColorSpan;
    private SpannableString mSpannableString;
    String title = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final ColorDrawable cd = new ColorDrawable(getResources().getColor(R.color.app_primary_accent));
        setToolbar(cd);
        cd.setAlpha(0);

        ScrollViewHelper scrollViewHelper = (ScrollViewHelper)findViewById(R.id.scrollViewHelper);
        scrollViewHelper.setOnScrollViewListener(new ScrollViewHelper.OnScrollViewListener() {

            @Override
            public void onScrollChanged(ScrollViewHelper v, int l, int t, int oldl, int oldt) {
                setTitleAlpha(255 - getAlphaforActionBar(v.getScrollY()));
                cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
            }

            private int getAlphaforActionBar(int scrollY) {
                int minDist = 0, maxDist = 550;
                if (scrollY > maxDist) {
                    return 255;
                } else {
                    if (scrollY < minDist) {
                        return 0;
                    } else {
                        return (int) ((255.0 / maxDist) * scrollY);
                    }
                }
            }


        });

        mSpannableString = new SpannableString(title);
        mAlphaForegroundColorSpan = new AlphaForeGroundColorSpan(0xFFFFFF);
    }


    private void setTitleAlpha(float alpha) {
        if(alpha<1){ alpha = 1; }
        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(mSpannableString);
    }

    private void setToolbar(ColorDrawable cd){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(cd);
        getSupportActionBar().setTitle(R.string.title_hotels);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();

                Intent intent = new Intent();
                intent.setClass(TestActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
