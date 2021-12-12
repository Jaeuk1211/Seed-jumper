package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class clearStage extends AppCompatActivity {

    int level, genre = 0;
    FirebaseUser loginUser;
    String uid;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = userDatabase.getReference();
    TextView stage;
    long totalTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear);
        stage = findViewById(R.id.stageName);

        loginUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = loginUser != null ? loginUser.getUid() : null;

        Intent itmeIntent = getIntent();
        level = itmeIntent.getIntExtra("level", 0);
        genre = itmeIntent.getIntExtra("genre", 0);
        totalTime = itmeIntent.getIntExtra("totalTime", 0);


        switch (genre){

            case 1://pop-rs
                stage.setText("Pop Lv." + level);
                break;

            case 2://dance - fs
                stage.setText("Dance Lv." + level);
                break;

            case 3://country -sf
                stage.setText("Country Lv." + level);
                break;

            case 4://rock -tp
                stage.setText("Rock Lv." + level);
                break;

            case 5://hiphop -dl
                stage.setText("Hiphop Lv." + level);
                break;
        }//switch

    }
}
