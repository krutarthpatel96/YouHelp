package com.abcdroid.helpapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    int x=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences= getSharedPreferences("k",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
         x=sharedPreferences.getInt("Isfirst",0);

        if(x==0)
        {
            Intent intent = new Intent(this, AppIntroScreen.class);
            editor.putInt("Isfirst",1);
            editor.commit();
            startActivity(intent);
            finish();

        }
        else
        {
            Intent intent = new Intent(this, WelcomeScreen.class);
            startActivity(intent);
            finish();

        }

    }
}
