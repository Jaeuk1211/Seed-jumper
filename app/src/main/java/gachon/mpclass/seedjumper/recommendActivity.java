package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class recommendActivity extends AppCompatActivity {

    int genre = 1;
    int level = 1;
    TextView genreName, levelInfo, userName;
    FirebaseAuth firebaseAuth;
    private String uid;
    DatabaseReference user;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = userDatabase.getReference();
    String[][] enableStage = new String[3][5];
    long avgTime = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);


        userName = findViewById(R.id.userId);
        genreName = findViewById(R.id.genreName);
        levelInfo = findViewById(R.id.levelInfo);

        firebaseAuth = FirebaseAuth.getInstance();//get instance to firebaseAuth
        uid = firebaseAuth.getCurrentUser().getUid();
        user = FirebaseDatabase.getInstance().getReference("users").child(uid);
        user.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                userName.setText(name + " ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("fragmentMyPage", String.valueOf(databaseError.toException())); // ????????? ??????
            }
        });

        //??????????????? ?????? ?????? ?????? ?????????, ?????? ???????????????????????? ????????????
        DatabaseReference clearStage = userReference.child("users").child(uid).child("record").child("clearStage");
        clearStage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String code = ds.getValue(String.class);
                    Log.d("clearStage0", code);
                    String levelIndex = code.substring(0,1);
                    String genreIndex = code.substring(1);
                    enableStage[Integer.parseInt(levelIndex) - 1][Integer.parseInt(genreIndex) - 1] = code;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference averageTime = userReference.child("users").child(uid).child("record").child("average");
        averageTime.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    Long a = ds.getValue(Long.class);
                    Long b = ds.getValue(Long.class);
                    avgTime = ds.getValue(Long.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        if(avgTime > 180)
        {
            level = 1;
        }
        else if(avgTime > 300)
        {
            level = 2;
        }
        else if(avgTime > 420)
        {
            level = 3;
        }

        Random random = new Random();
        for(int k = 0; k < 5; k++)
        {
            int i = random.nextInt(4) + 1;
            if(enableStage[level][i] != null){
                genre = i;
                break;
            }
        }

        Log.d("clearStage1", Integer.toString(genre));


        switch (genre){

            case 1://pop-rs
                genreName.setText("???????????? ???");
                break;

            case 2://dance - fs
                genreName.setText("????????? ??????");
                break;

            case 3://country -sf
                genreName.setText("????????? ?????????");
                break;

            case 4://rock -tp
                genreName.setText("???????????? ???");
                break;

            case 5://hiphop -dl
                genreName.setText("??????????????? ??????");
                break;
        }//switch

        levelInfo.setText(Integer.toString(level));


    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //??????????????? ????????? ????????? ???????????? ??? ??? ???
                break;
            case MotionEvent.ACTION_MOVE:
                //?????? ??? ???????????? ????????? ??? ??? ???
                break;
            case MotionEvent.ACTION_UP:
                Intent intent = new Intent(getApplicationContext(), challangeSplash.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("level", level);
                intent.putExtra("genre", genre);
                startActivity(intent);
                finish();
                break;
            case MotionEvent.ACTION_CANCEL:
                // ????????? ????????? ??? ??? ???
                break;
            default:
                break;
        }
        return true;
    }
}
