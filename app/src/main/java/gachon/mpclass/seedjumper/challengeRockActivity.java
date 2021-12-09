package gachon.mpclass.seedjumper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class challengeRockActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_challenge_rock);

        Button btn1 = (Button) findViewById(R.id.lev1);
        Button btn2 = (Button) findViewById(R.id.lev2);
        Button btn3 = (Button) findViewById(R.id.lev3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}