package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class fragmentHome extends Fragment {

    private TextView amount_calorie_consumption;
    private Button normal_btn;
    private Button recommend_btn;

    FirebaseAuth firebaseAuth;
    private String uid;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();//get instance to firebaseAuth
        uid = firebaseAuth.getCurrentUser().getUid();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        amount_calorie_consumption = view.findViewById(R.id.amount_calorie_consumption);
        normal_btn = (Button) view.findViewById(R.id.normal_exercise);
        recommend_btn = (Button) view.findViewById(R.id.recommend_exercise);

        //일반 운동 모드(Endless mode)
        normal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ExerciseActivity.class);
                view.getContext().startActivity(intent);

            }
        });

        //운동 추천 모드
        recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), recActivity.class);
                view.getContext().startActivity(intent);

            }
        });

        //사용자의 총 칼로리 소모량 세팅 (이후 하루 운동 소모량으로 수정 예정)
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);
        databaseReference.child("record").child("total").child("calorie").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int calorie = (int) dataSnapshot.getValue(Integer.class);
                //Record record = dataSnapshot.getValue(Record.class);
                amount_calorie_consumption.setText(calorie + "kcal");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("fragmentHome", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Record record = snapshot.getValue(Record.class);
//                    amount_calorie_consumption.setText(record.getCalorieConsumption());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });


}