package gachon.mpclass.seedjumper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class gardenActivity extends AppCompatActivity {

    boolean i = true;
    ImageView plant;
    View decorView;
    private int	uiOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);

        plant = findViewById(R.id.plantCoordi1);


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
