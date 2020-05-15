package com.abcdroid.helpapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class AppIntroScreen extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {

        addSlide(AppIntroFragment.newInstance("Welcome", "Now you can report something to NGO\n whenever you want", R.drawable.ic_logo, Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("Not limited", "YouHelp isn't aligned to any specific NGO. With YouHelp you can message from among a distinct group of NGOs.", R.drawable.ic_send_white, Color.parseColor("#3F51B5")));
        addSlide(AppIntroFragment.newInstance("Get started", "Shoot a photo from within the app and send it to a NGO.", R.drawable.ic_camera_white, Color.parseColor("#3F51B5")));
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed() {
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivity(intent);
    }

    @Override
    public void onDonePressed() {
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
    }

    @Override
    public void onSlideChanged() {
    }

    @Override
    public void onNextPressed() {
    }
}
