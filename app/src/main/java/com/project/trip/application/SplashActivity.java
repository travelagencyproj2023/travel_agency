package com.project.trip.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class SplashActivity extends AppCompatActivity {
     // شاشة السبلاش
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //oncreate هذه تشتغل في بداية الواجهه
        super.onCreate(savedInstanceState);
        // لعرض شاشة السبلاش
        setContentView(R.layout.activity_splash);
        // هذا الهندلر يعمل على تاخير شاشة السبلاش بمقدار 2 ثواني
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //للانتقال الى الشاشه IntroActivity
                // بعد مرور ال 2 ثواني
                Intent intent=new Intent(SplashActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}