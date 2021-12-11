package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class challangeSplash extends AppCompatActivity {

    TextView count;
    private CountDownTimer countDownTimer;
    int second = 5;//시간변경 필요
    int level = 0;
    int genre = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_splash);

        count = findViewById(R.id.splashTxt4);
        countDownTimer();
        countDownTimer.start();

        Intent splashIntent = getIntent();
        level = splashIntent.getIntExtra("level", 0);
        genre = splashIntent.getIntExtra("genre", 0);
        Log.d("level", Integer.toString(level) + Integer.toString(genre));
    }
    private void loadingStart(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                Intent intent=new Intent(getApplicationContext(), ProgressExerciseActivity.class);
                intent.putExtra("level", level);
                intent.putExtra("genre", genre);
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