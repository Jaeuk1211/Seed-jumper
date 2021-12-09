package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class challengeActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_challenge);

        Button btn1 = (Button) findViewById(R.id.pop);
        Button btn2 = (Button) findViewById(R.id.dance);
        Button btn3 = (Button) findViewById(R.id.country);
        Button btn4 = (Button) findViewById(R.id.rock);
        Button btn5 = (Button) findViewById(R.id.hiphop);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), challengePopActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), challengeDanceActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), challengeCountryActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), challengeRockActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), challengeHiphopActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }
}
