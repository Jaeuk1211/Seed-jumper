package gachon.mpclass.seedjumper;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class fragmentMypage extends Fragment {

    View targetView;
    Button drawerButton;
    boolean drawerToggle;
    Button goToGarden;
    int quoteNum = 0;
    TextView quoteText;

    private TextView userName;
    private TextView totalTime;
    private TextView totalCount;
    private TextView totalCalorie;

    //스케줄러
    public CalendarView calendarView;
    public Button cha_Btn, del_Btn, save_Btn;
    public TextView dateTextView, planCalorieTextView, contentTextView;
    public EditText contentEditText, planCalorieEditText;
    private String str = null;
    private int num = 0;
    public String fdate=null;


    FirebaseAuth firebaseAuth;
    private String uid;
    DatabaseReference user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();//get instance to firebaseAuth
        uid = firebaseAuth.getCurrentUser().getUid();
        user = FirebaseDatabase.getInstance().getReference("users").child(uid);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        targetView = view.findViewById(R.id.content);
        drawerButton = view.findViewById(R.id.drawer);
        quoteText = view.findViewById(R.id.quote);
        userName = view.findViewById(R.id.user_name);
        totalTime = view.findViewById(R.id.totalTime);
        totalCount = view.findViewById(R.id.totalCount);
        totalCalorie = view.findViewById(R.id.totalCalorie);

        calendarView = view.findViewById(R.id.calendarView);
        dateTextView = view.findViewById(R.id.dateTextView);
        planCalorieTextView = view.findViewById(R.id.planCalorieTextView);
        contentTextView = view.findViewById(R.id.contentTextView);
        save_Btn = view.findViewById(R.id.save_Btn);
        del_Btn = view.findViewById(R.id.del_Btn);
        cha_Btn = view.findViewById(R.id.cha_Btn);
        planCalorieEditText = view.findViewById(R.id.planCalorieEditText);
        contentEditText = view.findViewById(R.id.contentEditText);


        Random random = new Random();
        quoteNum = random.nextInt(5);

        switch (quoteNum) {
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

        class DrawButtonClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                if (drawerToggle == false) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(targetView, "translationY", -1700);
                    objectAnimator.setDuration(500); //0.5초에 걸쳐 진행.
                    objectAnimator.start();
                } else {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(targetView, "translationY", 0);
                    objectAnimator.setDuration(500); //0.5초에 걸쳐 진행.
                    objectAnimator.start();
                }
                drawerToggle = !drawerToggle;
            }
        }

        drawerButton.setOnClickListener(new DrawButtonClickListener());

        //사용자의 이름 불러오기
        user.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                userName.setText(name + "님 반갑습니다");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("fragmentMypage", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        //누적 운동시간, 칼로리 소모량, 줄넘기 횟수
        user.child("record").child("total").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Record record = dataSnapshot.getValue(Record.class);

                double calorie;
                int count, time;

                calorie = record.getCalorie();
                count = record.getCount();
                time = record.getTime() / 60;

                //텍스트뷰에 받아온 문자열 대입하기
                totalCalorie.setText(calorie + "kcal");
                totalCount.setText(count + "회");
                totalTime.setText(time + "분");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("fragmentMypage", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        //스케줄러부분
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dateTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contentEditText.setVisibility(View.VISIBLE);
                contentTextView.setVisibility(View.GONE);
                planCalorieEditText.setVisibility(View.VISIBLE);
                planCalorieTextView.setVisibility(View.GONE);
                cha_Btn.setVisibility(View.GONE);
                del_Btn.setVisibility(View.GONE);
                dateTextView.setText(String.format("%d-%d-%d", year, month + 1, dayOfMonth));
                contentEditText.setText("");
                planCalorieEditText.setText("");
                checkDay(year, month, dayOfMonth);
            }
        });

        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = contentEditText.getText().toString().trim();
                contentTextView.setText(str);
                num = Integer.parseInt(planCalorieEditText.getText().toString().trim());
                planCalorieTextView.setText(num);

                saveMemo(fdate,num,str);

                save_Btn.setVisibility(View.GONE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contentEditText.setVisibility(View.GONE);
                planCalorieEditText.setVisibility(View.GONE);
                contentTextView.setVisibility(View.VISIBLE);
                planCalorieTextView.setVisibility(View.VISIBLE);

            }
        });

        return view;
    }

    public void  checkDay(int cYear,int cMonth,int cDay){

        fdate = cYear+"-"+(cMonth+1)+"-"+cDay;
        user.child("record").child("daily").child(fdate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Memo memo = dataSnapshot.getValue(Memo.class);
                num = memo.getPlanCalorie();
                str = memo.getContent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("fragmentMypage", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        planCalorieEditText.setVisibility(View.GONE);
        contentEditText.setVisibility(View.GONE);
        contentTextView.setVisibility(View.VISIBLE);
        contentTextView.setText(str);
        planCalorieTextView.setVisibility(View.VISIBLE);
        planCalorieTextView.setText(num);

        save_Btn.setVisibility(View.GONE);
        cha_Btn.setVisibility(View.VISIBLE);
        del_Btn.setVisibility(View.VISIBLE);

        cha_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentEditText.setVisibility(View.VISIBLE);
                contentTextView.setVisibility(View.GONE);
                contentEditText.setText(str);
                planCalorieEditText.setVisibility(View.VISIBLE);
                planCalorieTextView.setVisibility(View.GONE);
                planCalorieEditText.setText(num);

                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.GONE);
                del_Btn.setVisibility(View.GONE);

                str = contentEditText.getText().toString().trim();
                num = Integer.parseInt(planCalorieEditText.getText().toString().trim());
                contentTextView.setText(str);
                planCalorieTextView.setText(num);


            }

        });
        del_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentTextView.setVisibility(View.GONE);
                contentEditText.setText("");
                contentEditText.setVisibility(View.VISIBLE);
                planCalorieTextView.setVisibility(View.GONE);
                planCalorieEditText.setText("");
                planCalorieEditText.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.GONE);
                del_Btn.setVisibility(View.GONE);
                removeMemo(fdate);
            }
        });
        if(contentTextView.getText()==null && planCalorieTextView.getText()==null){
            contentTextView.setVisibility(View.GONE);
            planCalorieTextView.setVisibility(View.GONE);
            dateTextView.setVisibility(View.VISIBLE);
            save_Btn.setVisibility(View.VISIBLE);
            cha_Btn.setVisibility(View.GONE);
            del_Btn.setVisibility(View.GONE);
            contentEditText.setVisibility(View.VISIBLE);
            planCalorieEditText.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("WrongConstant")
    public void removeMemo(String readDay){
        Memo memo = new Memo(0, null);
        user.child("record").child("daily").child(readDay).setValue(memo);
    }

    @SuppressLint("WrongConstant")
    public void saveMemo(String readDay, int calorie, String content){
        Memo memo = new Memo(calorie, content);
        user.child("record").child("daily").child(readDay).setValue(memo);
    }


}
