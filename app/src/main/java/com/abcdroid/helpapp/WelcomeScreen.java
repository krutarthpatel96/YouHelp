package com.abcdroid.helpapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {
ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle(" Home");
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(Html.fromHtml("<b>Hi</b> and once again welcome to the <b>YouHelp</b> app.\nTo start reporting press the camera button on the bottom right.\nAnd select the concerned department and Send it via our servers."));

    }

    public void Fab_Click(View view)
    {
        Intent i=new Intent(WelcomeScreen.this,FormActivity.class);
        startActivity(i);

    }
}
