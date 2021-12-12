package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class getNewItem extends AppCompatActivity {

    int level, genre = 0;
    FirebaseUser loginUser;
    String uid;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = userDatabase.getReference();
    ImageView itme;
    TextView celebration;
    long totalTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        itme = findViewById(R.id.getItem);
        celebration = findViewById(R.id.itemName);

        loginUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = loginUser != null ? loginUser.getUid() : null;

        Intent itemIntent = getIntent();
        level = itemIntent.getIntExtra("level", 0);
        genre = itemIntent.getIntExtra("genre", 0);
        totalTime = itemIntent.getIntExtra("totalTime", 0);


        switch (genre){

            case 0:
                if(totalTime > 10800)
                {
                    userReference.child("users").child(uid).child("garden").child("badge").child("lamp").setValue(16);
                    itme.setImageResource(R.drawable.lamp);
                    celebration.setText("Lamp");
                }
                else if(totalTime > 7200){
                    userReference.child("users").child(uid).child("garden").child("badge").child("chair").setValue(15);
                    itme.setImageResource(R.drawable.chair);
                    celebration.setText("Chair");
                }
                else{
                    userReference.child("users").child(uid).child("garden").child("badge").child("fence").setValue(17);
                    itme.setImageResource(R.drawable.fence);
                    celebration.setText("Fence");
                }
                break;

            case 1://pop-rs
                if(level == 1){
                    userReference.child("users").child(uid).child("garden").child("badge").child("rs").setValue(3);
                    itme.setImageResource(R.drawable.seed1);
                    celebration.setText("Rose LV.1");
                }
                else if(level ==2){
                    userReference.child("users").child(uid).child("garden").child("badge").child("rs").setValue(4);
                    itme.setImageResource(R.drawable.one_rose);
                    celebration.setText("Rose LV.2");
                }
                else if(level ==3){
                    userReference.child("users").child(uid).child("garden").child("badge").child("rs").setValue(5);
                    itme.setImageResource(R.drawable.bundle_rose);
                    celebration.setText("Rose LV.3");
                }
                break;

            case 2://dance - fs
                if(level == 1){
                    userReference.child("users").child(uid).child("garden").child("badge").child("fs").setValue(12);
                    itme.setImageResource(R.drawable.seed4);
                    celebration.setText("Freesia LV.1");
                }
                else if(level ==2){
                    userReference.child("users").child(uid).child("garden").child("badge").child("fs").setValue(13);
                    itme.setImageResource(R.drawable.one_yellowfreesia4);
                    celebration.setText("Freesia LV.2");
                }
                else if(level ==3){
                    userReference.child("users").child(uid).child("garden").child("badge").child("fs").setValue(14);
                    itme.setImageResource(R.drawable.bundle_freesia);
                    celebration.setText("Freesia LV.3");
                }
                break;

            case 3://country -sf
                if(level == 1){
                    userReference.child("users").child(uid).child("garden").child("badge").child("sf").setValue(9);
                    itme.setImageResource(R.drawable.seed3);
                    celebration.setText("Sunflower LV.1");
                }
                else if(level ==2){
                    userReference.child("users").child(uid).child("garden").child("badge").child("sf").setValue(10);
                    itme.setImageResource(R.drawable.one_sunflower);
                    celebration.setText("Sunflower LV.2");
                }
                else if(level ==3){
                    userReference.child("users").child(uid).child("garden").child("badge").child("sf").setValue(11);
                    itme.setImageResource(R.drawable.bundle_sunflower);
                    celebration.setText("Sunflower LV.3");
                }
                break;

            case 4://rock -tp
                if(level == 1){
                    userReference.child("users").child(uid).child("garden").child("badge").child("tp").setValue(7);
                    itme.setImageResource(R.drawable.seed2);
                    celebration.setText("Tulip LV.1");
                }
                else if(level ==2){
                    userReference.child("users").child(uid).child("garden").child("badge").child("tp").setValue(8);
                    itme.setImageResource(R.drawable.one_tulip);
                    celebration.setText("Tulip LV.2");
                }
                else if(level ==3){
                    userReference.child("users").child(uid).child("garden").child("badge").child("tp").setValue(9);
                    itme.setImageResource(R.drawable.bundle_whitetulip);
                    celebration.setText("Tulip LV.3");
                }
                break;

            case 5://hiphop -dl
                if(level == 1){
                    userReference.child("users").child(uid).child("garden").child("badge").child("dl").setValue(0);
                    itme.setImageResource(R.drawable.seed6);
                    celebration.setText("Dandelion LV.1");
                }
                else if(level ==2){
                    userReference.child("users").child(uid).child("garden").child("badge").child("dl").setValue(1);
                    itme.setImageResource(R.drawable.one_dandelion2);
                    celebration.setText("Dandelion LV.2");
                }
                else if(level ==3){
                    userReference.child("users").child(uid).child("garden").child("badge").child("dl").setValue(2);
                    itme.setImageResource(R.drawable.bundle_dandelion);
                    celebration.setText("Dandelion LV.3");
                }
                break;
        }//switch


    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //손가락으로 화면을 누르기 시작했을 때 할 일
                break;
            case MotionEvent.ACTION_MOVE:
                //터치 후 손가락을 움직일 때 할 일
                break;
            case MotionEvent.ACTION_UP:
                Intent intent = new Intent(getApplicationContext(), clearStage.class);
                intent.putExtra("level", level);
                intent.putExtra("genre", genre);
                startActivity(intent);
                finish();
                break;
            case MotionEvent.ACTION_CANCEL:
                // 터치가 취소될 때 할 일
                break;
            default:
                break;
        }
        return true;
    }
}
