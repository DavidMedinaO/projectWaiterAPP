package com.example.waiterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private AnimationDrawable anim, anim2;
    private Button button, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();

        anim = (AnimationDrawable) button.getBackground();
        anim.setEnterFadeDuration(2300);
        anim.setExitFadeDuration(2300);

        anim2 = (AnimationDrawable) button2.getBackground();
        anim2.setEnterFadeDuration(2800);
        anim2.setExitFadeDuration(2800);

    }

    private void init(){
        this.button = findViewById(R.id.button);
        this.button2 = findViewById(R.id.button2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(anim != null && !anim.isRunning()){
            anim.start();
        }
        if(anim2 != null && !anim2.isRunning()){
            anim2.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(anim != null && anim.isRunning()){
            anim.stop();
        }
        if(anim2 != null && anim2.isRunning()){
            anim2.stop();
        }
    }
}