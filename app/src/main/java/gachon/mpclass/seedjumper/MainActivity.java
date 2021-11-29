package gachon.mpclass.seedjumper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    fragmentHome homeFragment;
    fragmentGarden gardenFragment;
    fragmentMypage mypageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        homeFragment = new fragmentHome();
        gardenFragment = new fragmentGarden();
        mypageFragment = new fragmentMypage();

        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout, homeFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout,homeFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.tab2:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout,gardenActivity).commitAllowingStateLoss();
                        Intent intent = new Intent(getApplicationContext(), gardenActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.mainLayout,mypageFragment).commitAllowingStateLoss();
                        return true;
                }
                return false;
            }
        });

    }

}