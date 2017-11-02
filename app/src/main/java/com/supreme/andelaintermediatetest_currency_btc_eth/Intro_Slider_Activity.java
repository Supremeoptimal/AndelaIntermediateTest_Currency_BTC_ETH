package com.supreme.andelaintermediatetest_currency_btc_eth;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

public class Intro_Slider_Activity extends AppIntro2 {

    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(this);
        if (!preferenceManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        //noinspection deprecation
        addSlide(AppIntro2Fragment.newInstance("Bitcoin", "A real time bitcoin currency conversion", R.drawable.btc, getResources().getColor(R.color.slide_1)));
        //noinspection deprecation
        addSlide(AppIntro2Fragment.newInstance("Ethereum", "A real time Ethereum currency conversion", R.drawable.eth, getResources().getColor(R.color.slide_2)));
        //noinspection deprecation
        addSlide(AppIntro2Fragment.newInstance("Currency Conversion", "Convert your base currency to bitcoin and ethereum", R.drawable.slide_3, getResources().getColor(R.color.slide_3)));

        setSlideOverAnimation();
        showStatusBar(false);

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onDonePressed() {
        super.onDonePressed();
        launchHomeScreen();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        launchHomeScreen();

    }

    public void launchHomeScreen() {
        preferenceManager.setFirstTimeLaunch(false);
        startActivity(new Intent(Intro_Slider_Activity.this, Currency.class));
        finish();
    }

}
