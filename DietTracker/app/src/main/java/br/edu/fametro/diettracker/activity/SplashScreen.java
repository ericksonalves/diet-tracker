package br.edu.fametro.diettracker.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.fametro.diettracker.R;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_TIME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callLoginActivity();
            }
        }, SPLASH_DISPLAY_TIME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void callLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
