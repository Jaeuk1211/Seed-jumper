package com.example.seedjumper;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Garden extends AppCompatActivity {

    boolean i = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);


        ImageButton object = findViewById(R.id.change_object);
        object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                object.setSelected(i);
                i = !i;
            }
        });

    }
}
