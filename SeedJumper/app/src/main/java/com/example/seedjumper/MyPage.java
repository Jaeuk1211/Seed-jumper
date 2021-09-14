package com.example.seedjumper;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {

    View targetView;
    Button drawerButton;
    boolean drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        targetView = findViewById(R.id.content);
        drawerButton = findViewById(R.id.drawer);

        class DrawButtonClickListener implements View.OnClickListener{
            @Override
            public void onClick(View v){
                if(drawerToggle == false){
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(targetView, "translationY", -1900);
                    objectAnimator.setDuration(500); //0.5초에 걸쳐 진행.
                    objectAnimator.start();
                }
                else{
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(targetView, "translationY", 0);
                    objectAnimator.setDuration(500); //0.5초에 걸쳐 진행.
                    objectAnimator.start();
                }
                drawerToggle = !drawerToggle;
            }
        }

        drawerButton.setOnClickListener(new DrawButtonClickListener());

    }
}
