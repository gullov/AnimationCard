  package ru.gulov.animationcard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    FrameLayout container;
    public boolean isShowed = false;
    FragmentNum newFragment;
    ArrayList<Integer> characters = new ArrayList<>();
    public int count = 0, startCount = 0;
    boolean isShowNumbers = false;


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        if(pref.getBoolean("main1", false)){
            for (int i = 0 ; i<pref.getInt("main2", 0);i++){
                characters.add(pref.getInt("mainNumber"+i, 1));
            }
            isShowNumbers = true;
        }

        Fragment f0 = new FragmentMain();


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, f0);
        ft.commit();


    }

    @Override
    protected void onPause() {
        if(isShowNumbers) {
            editor.putBoolean("main1", true);
            editor.putInt("main2", characters.size());
            for (int i = 0; i < characters.size(); i++) {
                editor.putInt("mainNumber" + i, characters.get(i));
            }
            editor.apply();
        }
        super.onPause();
    }

    @Override
    protected void onStart() {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onStart();
    }

    public void ShowNum() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.design_bottom_sheet_slide_in1, R.anim.design_bottom_sheet_slide_out1);

        newFragment = new FragmentNum();

        ft.replace(R.id.container, newFragment, "detailFragment");

        ft.commit();
    }

    public void ShowMain() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.design_bottom_sheet_slide_out12, R.anim.design_bottom_sheet_slide_in12);


        FragmentMain newFragment = new FragmentMain();

        ft.replace(R.id.container, newFragment, "detailFragment");

        ft.commit();
    }

    public void ShowRandom() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.design_bottom_sheet_slide_out12, R.anim.design_bottom_sheet_slide_in12);

        FragmentRandom newFragment = new FragmentRandom();
        ft.replace(R.id.container, newFragment, "detailFragment");
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (isShowed){
//            newFragment.circularReActivity();
            isShowed = false;
        }
        else {
            ShowMain();
        }
    }

}