package gachon.mpclass.seedjumper;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class fragmentGarden extends Fragment {

    boolean i = true;
    ImageView plant;
    View decorView;
    private int	uiOption;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_garden, container, false);

        plant = view.findViewById(R.id.plantCoordi1);


        ImageButton object = view.findViewById(R.id.change_object);
        object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                object.setSelected(i);
                i = !i;
            }
        });

        ImageButton test = view.findViewById(R.id.plant_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plant.setImageResource(R.drawable.garden_plant1);
                plant.setX(360);
                plant.setY(15);
            }
        });



        return view;
    }

}