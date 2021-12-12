package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.Bundle;
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

        Intent itmeIntent = getIntent();
        level = itmeIntent.getIntExtra("level", 0);
        genre = itmeIntent.getIntExtra("genre", 0);
        totalTime = itmeIntent.getIntExtra("totalTime", 0);


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
}
