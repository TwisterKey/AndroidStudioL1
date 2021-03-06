package com.example.logineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_splash_screen);

        Intent intent = new Intent(SplashScreenActivity.this, ListaProduseActivity.class);

        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SplashScreenActivity.this.startActivity(intent);
                    finish();
                }
            }
        };

        thread.start();
    }
}