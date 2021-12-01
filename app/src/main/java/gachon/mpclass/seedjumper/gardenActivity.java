package gachon.mpclass.seedjumper;
//운동페이지 완성으로 파이어베이스에 뱃지 추가 기능 만들고나서 여기 파이어베이스 만들기
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class gardenActivity extends AppCompatActivity {

    boolean i = true;
    private RecyclerView mRecyclerView;
    boolean[] enable = {true, true, true, true, true, true, true, true, true};
    int[][] location = {{370, 240}, {220, 340}, {520, 340}, {30, 450}, {360, 460}, {680, 450}, {210, 570}, {520, 570}, {400, 690}};
    int x, y;
    private String[] plantName = {"dl", "rs", "tp", "sf", "fs"};
    int count =0;
    ImageView[] iv = new ImageView[9];
    int addIv = 0;
    FrameLayout garden;
    FirebaseUser loginUser;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = userDatabase.getReference();
    private ArrayList<Garden> userInfo = new ArrayList<Garden>();
    String flowers, gardenInfo;
    int k =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);
        loginUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = loginUser != null ? loginUser.getUid() : null;

        DatabaseReference badge = userReference.child("users").child(uid).child("garden");
        Log.d("rental", "why?");
        badge.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("rental", "why!");
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Log.d("rental", uid);
                    flowers = ds.child("badge").getValue(String.class);
                    gardenInfo = ds.child("gardenInfo").getValue(String.class);
                    userInfo.add(new Garden(flowers, gardenInfo));
                    k++;
                    if(flowers == null)
                    {
                        Log.d("rental", "fail");
                    }
                    else{
                        Log.d("rental", flowers);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("rental11", Integer.toString(k));
            }
        });

        Log.d("rental", "pass");
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

        ImageButton clean =(ImageButton) findViewById(R.id.clean_all);
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count > 0)
                {
                    for(int i = 0; i < count; i++)
                    {
                        iv[i].setImageResource(0);
                        iv[i] = null;
                        Log.d("cleaning. . . .", Integer.toString(i) + "count: " + Integer.toString(count));
                    }
                    Arrays.fill(enable, true);//파이어베이스 연결
                    count = 0;
                }
                else{
                    Toast.makeText(getApplicationContext(),"정원이 비어있습니다.\n먼저 정원을 채워주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        adapter.setItemClickListener(new OnItemClickListener(){
            @Override
            public void OnItemClick(plantObjectViewAdapter.ViewHolder holder, View view, int pos){
                DisplayMetrics metrics = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                windowManager.getDefaultDisplay().getMetrics(metrics);
                //Toast.makeText(getApplicationContext(),"아이템 선택 " + name, Toast.LENGTH_SHORT).show();
                String flowerName = "noname";
                if(!adapter.getName(pos).equals("noname") && count < 9){
                    for(int i = 0; i< 9; i++)
                    {
                        if(iv[i] == null){
                            addIv = i;
                            Log.d("imageview", Integer.toString(addIv) + " view is full........");
                            break;
                        }
                    }
                    switch (pos){
                        case 0:
                            iv[addIv] = new ImageView(view.getContext());
                            iv[addIv].setImageResource(R.drawable.seed6);
                            flowerName = "dl1";
                            break;
                        case 1:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_dandelion2);
                            flowerName = "dl2";
                            break;
                        case 2:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_dandelion);
                            flowerName = "dl3";
                            break;
                        case 3:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed1);
                            flowerName = "rs1";
                            break;
                        case 4:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_rose);
                            flowerName = "rs2";
                            break;
                        case 5:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_rose);
                            flowerName = "rs3";
                            break;
                        case 6:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed2);
                            flowerName = "tp1";
                            break;
                        case 7:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_tulip);
                            flowerName = "tp2";
                            break;
                        case 8:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_whitetulip);
                            flowerName = "tp3";
                            break;
                        case 9:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed3);
                            flowerName = "sf1";
                            break;
                        case 10:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_sunflower);
                            flowerName = "sf2";
                            break;
                        case 11:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_sunflower);
                            flowerName = "sf3";
                            break;
                        case 12:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed4);
                            flowerName = "fr1";
                            break;
                        case 13:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_yellowfreesia4);
                            flowerName = "fr2";
                            break;
                        case 14:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_freesia);
                            flowerName = "fr3";
                            break;
                    }

                    iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                    location(count);
                    iv[addIv].setX(x);
                    iv[addIv].setY(y);
                    garden.addView(iv[addIv]);
                    count ++;

                    userReference.child("users").child(uid).child("garden").child("gardenInfo").setValue(addIv);
                    userReference.child("users").child(uid).child("garden").child("gardenInfo").child(Integer.toString(addIv)).setValue(flowerName);
                }
            }
        });



    }

    public void location(int count){
        int k=0;

        if(count == 0)
        {
            x = location[0][0];
            y = location[0][1];
            enable[0] = false;
        }
        else if(count < 4)
        {
            for(k =1; k< 4; k++)
            {
                if(enable[k] == true)
                {
                    x = location[k][0];
                    y = location[k][1];
                    Log.d("location", Integer.toString(k));
                    break;
                }
            }
            enable[k] = false;
        }
        else if(count < 7)
        {
            for(k =4; k< 7; k++)
            {
                if(enable[k] == true)
                {
                    x = location[k][0];
                    y = location[k][1];
                    break;
                }
            }
            enable[k] = false;
        }
        else if(count < 9)
        {
            for(k =7; k< 9; k++)
            {
                if(enable[k] == true)
                {
                    x = location[k][0];
                    y = location[k][1];
                    break;
                }
            }
            enable[k] = false;
        }
        else{
            x = location[8][0];
            y = location[8][1];
            enable[8] = false;
        }
    }
}

