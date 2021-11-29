package gachon.mpclass.seedjumper;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class fragmentMypage extends Fragment{

    View targetView;
    Button drawerButton;
    boolean drawerToggle;
    Button goToGarden;
    int quoteNum = 0;
    TextView quoteText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        targetView = view.findViewById(R.id.content);
        drawerButton = view.findViewById(R.id.drawer);
        quoteText = view.findViewById(R.id.quote);

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
