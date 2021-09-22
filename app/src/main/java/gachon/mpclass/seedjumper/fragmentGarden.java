package gachon.mpclass.seedjumper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class fragmentGarden extends AppCompatActivity {

    boolean i = true;
    ImageView plant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_garden);

        plant = findViewById(R.id.plantCoordi);


        ImageButton object = findViewById(R.id.change_object);
        object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                object.setSelected(i);
                i = !i;
            }
        });

        ImageButton test = findViewById(R.id.plant_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plant.setImageResource(R.drawable.garden_plant1);
                plant.setX(360);
                plant.setY(15);
            }
        });

    }
}