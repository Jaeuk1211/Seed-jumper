package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseSplash extends AppCompatActivity {

    TextView count;
    private CountDownTimer countDownTimer;
    int second = 5;//시간변경 필요

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_splash);

        count = findViewById(R.id.splashTxt4);
        countDownTimer();
        countDownTimer.start();
    }
    private void loadingStart(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                Intent intent=new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(intent);
                finish();
            }
        },0);//시간변경 필요
    }

    public void countDownTimer(){
        countDownTimer = new CountDownTimer(6000, 1000) {//시간변경 필요
            public void onTick(long millisUntilFinished) {
                count.setText(String.valueOf(second));
                second--;
            }
            public void onFinish() {
                count.setText(String.valueOf(0));
                loadingStart();
            }
        };
    }

    @Override

    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }


}