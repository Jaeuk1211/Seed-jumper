package gachon.mpclass.seedjumper;
//운동페이지 완성으로 파이어베이스에 뱃지 추가 기능 만들고나서 여기 파이어베이스 만들기
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class  gardenActivity extends AppCompatActivity {

    boolean i = true;
    private RecyclerView mRecyclerView;
    boolean[] enable = {true, true, true, true, true, true, true, true, true};
    int[][] location = {{370, 240}, {220, 340}, {540, 340}, {30, 450}, {360, 460}, {690, 460}, {210, 570}, {540, 570}, {400, 690}};
    private String[] plantName = {"dl", "rs", "tp", "sf", "fs"};
    int x, y;
    private boolean[] enableItem = new boolean[18];
    int count =0;
    ImageView[] iv = new ImageView[9];
    int addIv = 0;
    FrameLayout garden;
    FirebaseUser loginUser;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = userDatabase.getReference();
    String flowers;
    int k =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);

        Arrays.fill(enableItem, false);

        loginUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = loginUser != null ? loginUser.getUid() : null;

        garden = findViewById(R.id.top_garden);
        final int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        final int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

        plantObjectViewAdapter adapter = new plantObjectViewAdapter();
        //사용할수있는 아이템 가져오는 코드
        DatabaseReference badge = userReference.child("users").child(uid).child("garden").child("badge");
        badge.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    long code = ds.getValue(Long.class);//아이템 획득 기능이 추가되면 거기 맞추어 타입 바꾸기
                    enableItem[(int)code] = true;
                }

                for(int i= 0; i <18; i++)
                {
                    if(enableItem[i] == true)
                    {
                        adapter.addItem(Integer.toString(i));
                    }
                    else {
                        adapter.addItem("noname");
                    }
                }

                adapter.notifyDataSetChanged();

                mRecyclerView = (RecyclerView) findViewById(R.id.objects_listview);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(adapter);
                //정원 정보 가져와 나타내기
                setGarden(uid, width, height);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //리사이클러뷰 표시
        ImageButton object = findViewById(R.id.change_object);
        object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                object.setSelected(i);
                i = !i;
                if(i == false){
                    mRecyclerView.smoothScrollToPosition(17);
                }
                else{
                    mRecyclerView.smoothScrollToPosition(0);
                }
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
                    }
                    Arrays.fill(enable, true);//파이어베이스 연결
                    count = 0;
                    userReference.child("users").child(uid).child("garden").child("gardenInfo").removeValue();
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
                            break;
                        }
                    }
                    switch (pos){
                        case 0:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed6);
                            flowerName = "0";
                            break;
                        case 1:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_dandelion2);
                            flowerName = "1";
                            break;
                        case 2:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_dandelion);
                            flowerName = "2";
                            break;
                        case 3:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed1);
                            flowerName = "3";
                            break;
                        case 4:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_rose);
                            flowerName = "4";
                            break;
                        case 5:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_rose);
                            flowerName = "5";
                            break;
                        case 6:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed2);
                            flowerName = "6";
                            break;
                        case 7:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_tulip);
                            flowerName = "7";
                            break;
                        case 8:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_whitetulip);
                            flowerName = "8";
                            break;
                        case 9:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed3);
                            flowerName = "9";
                            break;
                        case 10:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_sunflower);
                            flowerName = "10";
                            break;
                        case 11:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_sunflower);
                            flowerName = "11";
                            break;
                        case 12:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed4);
                            flowerName = "12";
                            break;
                        case 13:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_yellowfreesia4);
                            flowerName = "13";
                            break;
                        case 14:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_freesia);
                            flowerName = "14";
                            break;
                        case 15:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.chair);
                            flowerName = "15";
                            break;
                        case 16:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.lamp);
                            flowerName = "16";
                            break;
                        case 17:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.fence);
                            flowerName = "17";
                            break;
                        case 18:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.empty_object);
                            flowerName = "18";
                            break;
                    }

                    iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                    location(count);
                    iv[addIv].setX(x);
                    iv[addIv].setY(y);
                    garden.addView(iv[addIv]);
                    count++;
                    userReference.child("users").child(uid).child("garden").child("gardenInfo").child(Integer.toString(addIv)).setValue(flowerName);
                }
                else if(adapter.getName(pos).equals("noname"))
                {
                    Toast.makeText(getApplicationContext(),"아직 획득하지 않은 아이템입니다.\n운동을 통해 아이템을 획득해 정원을 꾸며보세요!", Toast.LENGTH_SHORT).show();
                }
                else if(count == 9)
                {
                    Toast.makeText(getApplicationContext(),"정원이 가득 차 있습니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void setGarden(String uid, int width, int height){
        DatabaseReference gardenInfo = userReference.child("users").child(uid).child("garden").child("gardenInfo");
        gardenInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    flowers = ds.getValue(String.class);
                    for(int i = 0; i< 9; i++)
                    {
                        if(iv[i] == null){
                            addIv = i;
                            break;
                        }
                    }
                    switch (Integer.parseInt(flowers)){
                        case 0:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed6);
                            break;
                        case 1:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_dandelion2);
                            break;
                        case 2:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_dandelion);
                            break;
                        case 3:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed1);
                            break;
                        case 4:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_rose);
                            break;
                        case 5:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_rose);
                            break;
                        case 6:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed2);
                            break;
                        case 7:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_tulip);
                            break;
                        case 8:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_whitetulip);
                            break;
                        case 9:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed3);
                            break;
                        case 10:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_sunflower);
                            break;
                        case 11:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_sunflower);
                            break;
                        case 12:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.seed4);
                            break;
                        case 13:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.one_yellowfreesia4);
                            break;
                        case 14:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.bundle_freesia);
                            break;
                        case 15:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.chair);
                            break;
                        case 16:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.lamp);
                            break;
                        case 17:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.fence);
                            break;
                        case 18:
                            iv[addIv] = new ImageView(getApplicationContext());
                            iv[addIv].setImageResource(R.drawable.empty_object);
                            break;
                    }
                    iv[addIv].setLayoutParams(new ViewGroup.LayoutParams(width, height));
                    location(count);
                    iv[addIv].setX(x);
                    iv[addIv].setY(y);
                    garden.addView(iv[addIv]);
                    count ++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("rental11", Integer.toString(k));
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

