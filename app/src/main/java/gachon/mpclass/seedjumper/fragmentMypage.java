package gachon.mpclass.seedjumper;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class fragmentMypage extends Fragment{

    View targetView;
    Button drawerButton;
    boolean drawerToggle;
    Button goToGarden;
    int quoteNum = 0;
    TextView quoteText;

    private TextView userName;

    FirebaseAuth firebaseAuth;
    private String uid;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();//get instance to firebaseAuth
        uid = firebaseAuth.getCurrentUser().getUid();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        targetView = view.findViewById(R.id.content);
        drawerButton = view.findViewById(R.id.drawer);
        quoteText = view.findViewById(R.id.quote);
        userName = view.findViewById(R.id.user_name);

        Random random = new Random();
        quoteNum = random.nextInt(5);

        switch (quoteNum){
            case 0:
                quoteText.setText("Reading for mind \nnexercise for body");
                break;
            case 1:
                quoteText.setText("The hard days are \nwhat make you stronger");
                break;
            case 2:
                quoteText.setText("Don't wish for it \nwork for it");
                break;
            case 3:
                quoteText.setText("Slow And Steady,\nWins The Race.\n");
                break;
            case 4:
                quoteText.setText("department of software");
                break;
        }

        class DrawButtonClickListener implements View.OnClickListener{
            @Override
            public void onClick(View v){
                if(drawerToggle == false){
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(targetView, "translationY", -1700);
                    objectAnimator.setDuration(500); //0.5초에 걸쳐 진행.
                    objectAnimator.start();
                }
                else{
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(targetView, "translationY", 0);
                    objectAnimator.setDuration(500); //0.5초에 걸쳐 진행.
                    objectAnimator.start();
                }
                drawerToggle = !drawerToggle;
            }
        }

        drawerButton.setOnClickListener(new DrawButtonClickListener());

        //사용자의 이름 불러오기
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users").child(uid);
        user.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                userName.setText(name + "님 반갑습니다");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("fragmentHome", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });




//        goToGarden.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getActivity(), fragmentGarden.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }
}
