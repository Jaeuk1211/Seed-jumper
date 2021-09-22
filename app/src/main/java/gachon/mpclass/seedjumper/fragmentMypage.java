package gachon.mpclass.seedjumper;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
public class fragmentMypage extends Fragment{

    View targetView;
    Button drawerButton;
    boolean drawerToggle;
    Button goToGarden;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        targetView = view.findViewById(R.id.content);
        drawerButton = view.findViewById(R.id.drawer);
        goToGarden = view.findViewById(R.id.goToGarden);

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


        goToGarden.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), fragmentGarden.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
