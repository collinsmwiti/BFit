package com.example.collins.bfit.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.collins.bfit.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.signUp) Button mSignUpButton;
    @Bind(R.id.login) Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mSignUpButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

        VideoView videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test);
        videoview.setVideoURI(uri);
        videoview.start();
    }

    @Override
    public void onClick(View view) {
        if (view == mSignUpButton) {
            Intent intent = new Intent(SplashActivity.this, CreateAccountActivity.class);
            startActivity(intent);
            finish();
        }

        if (view == mLoginButton) {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
