package gachon.mpclass.seedjumper;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class gardenActivity extends AppCompatActivity {

    boolean i = true;
    ImageView plant;
    private RecyclerView mRecyclerView;
    private String[] plantName = {"ddl", "rs", "tlp", "sf", "frs"};
    int count =0;
    private ImageView[] iv = new ImageView[9];
    int addIv = 0;
    FrameLayout garden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);
        garden = findViewById(R.id.top_garden);
        final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        //plant = findViewById(R.id.plantCoordi1);
        mRecyclerView = (RecyclerView) findViewById(R.id.objects_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        plantObjectViewAdapter adapter = new plantObjectViewAdapter();
        for(int i = 0; i < 15; i++)
        {
            //파이어베이스에서 활성화된 아이템 목록 갖고오기
            adapter.addItem(plantName[i % 5]);
        }

        adapter.notifyDataSetChanged();

        mRecyclerView.setAdapter(adapter);

        ImageButton object = findViewById(R.id.change_object);
        object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                object.setSelected(i);
                i = !i;
            }
        });

        adapter.setItemClickListener(new OnItemClickListener(){
            @Override
            public void OnItemClick(plantObjectViewAdapter.ViewHolder holder, View view, int pos){
                DisplayMetrics metrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) getApplicationContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                windowManager.getDefaultDisplay().getMetrics(metrics);
                String name = adapter.getName(pos);
                Toast.makeText(getApplicationContext(),"아이템 선택 " + name, Toast.LENGTH_SHORT).show();
                if(!adapter.getName(pos).equals("noname") && count < 9){
                    for(int i =0; i< 9; i++)
                    {
                        if(iv[i] == null){
                            addIv = i;
                        }
                    }
                    switch (pos){
                        case 0:
                            iv[addIv] = new ImageView(view.getContext());
                            iv[addIv].setImageResource(R.drawable.seed6);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(380);
                            iv[addIv].setY(480);
                            garden.addView(iv[addIv]);

                            count ++;
                            break;
                        case 1:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_dandelion2);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(90);
                            iv[addIv].setY(500);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 2:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_dandelion);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(700);
                            iv[addIv].setY(520);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 3:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed1);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(380);
                            iv[addIv].setY(300);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 4:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_rose);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(380);
                            iv[addIv].setY(720);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 5:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_rose);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(240);
                            iv[addIv].setY(610);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 6:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed2);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(500);
                            iv[addIv].setY(690);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 7:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_tulip);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(480);
                            iv[addIv].setY(400);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 8:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_whitetulip);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(220);
                            iv[addIv].setY(390);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 9:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed3);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(590);
                            iv[addIv].setY(590);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 10:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_sunflower);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(20);
                            iv[addIv].setY(0);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 11:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_sunflower);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(20);
                            iv[addIv].setY(0);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 12:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed4);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(20);
                            iv[addIv].setY(0);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 13:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_yellowfreesia3);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(20);
                            iv[addIv].setY(0);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                        case 14:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_freesia);
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                            iv[addIv].setX(20);
                            iv[addIv].setY(0);
                            garden.addView(iv[addIv]);
                            count ++;
                            break;
                    }
                }
            }
        });

//        ImageButton test = findViewById(R.id.plant_test);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                plant.setImageResource(R.drawable.garden_plant1);
//                plant.setX(360);
//                plant.setY(15);
//            }
//        });

    }
}
