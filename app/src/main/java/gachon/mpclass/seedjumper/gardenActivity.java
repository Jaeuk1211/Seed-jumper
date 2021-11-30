package gachon.mpclass.seedjumper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class gardenActivity extends AppCompatActivity {

    boolean i = true;
    ImageView plant;
    private RecyclerView mRecyclerView;
    private ArrayList<plantObjectViewItem> mList;
    private plantObjectViewAdapter mRecyclerViewAdapter;
    private String[] plantName = {"ddl", "rs", "tlp", "sf", "frs"};
    int count =0;
    private ImageView[] iv = new ImageView[9];
    int addIv = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);

        plant = findViewById(R.id.plantCoordi1);

        mRecyclerView = (RecyclerView) findViewById(R.id.objects_listview);
        mList = new ArrayList<>();

        ArrayList<plantObjectViewItem> list = new ArrayList<>();
        for(int i = 0; i < 15; i++)
        {
            //파이어베이스에서 활성화된 아이템 목록 갖고오기
            addItem(plantName[i % 5]);
        }

        mRecyclerViewAdapter = new plantObjectViewAdapter(mList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        ImageButton object = findViewById(R.id.change_object);
        object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                object.setSelected(i);
                i = !i;
            }
        });

        mRecyclerViewAdapter.setOnItemClickListener(new plantObjectViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int pos){
                Log.d("activity", "success!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                if(!mRecyclerViewAdapter.getName(pos).equals("noname") && count < 10){
                    for(int i =0; i< 9; i++)
                    {
                        if(iv[i] == null){
                            addIv = i;
                        }
                    }
                    switch (pos){
                        case 0:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.seed6);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 1:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.one_dandelion2);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 2:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.bundle_dandelion);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 3:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.seed1);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 4:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.one_rose);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 5:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.bundle_rose);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 6:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.seed2);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 7:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.one_tulip);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 8:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.bundle_whitetulip);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 9:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.seed3);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 10:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.one_sunflower);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 11:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.bundle_sunflower);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 12:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.seed4);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 13:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.one_yellowfreesia3);
                            plant.setX(360);
                            plant.setY(15);
                            count ++;
                            break;
                        case 14:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            iv[addIv].setImageResource(R.drawable.bundle_freesia);
                            plant.setX(360);
                            plant.setY(15);
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

    public void addItem(String imgName){
        plantObjectViewItem item = new plantObjectViewItem();
        item.setPlantName(imgName);
        mList.add(item);
    }
}
