package ru.gulov.animationcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


public class RandomActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Integer> phNumber = new ArrayList<>();
    Integer[] callDurations;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            count = extras.getInt("count");
        }

        callDurations = new Integer[count];
        phNumber = new ArrayList<>();
        for(int i = 0 ; i<count;i++){
            callDurations[i] = i+1;
            Log.d("TAGssss", "onCreate: "+callDurations[i]);
        }
        phNumber.addAll(Arrays.asList(callDurations));
        recyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.smoothScrollToPosition(0);
        mAdapter = new RandomAdapter(getApplicationContext(),phNumber, (v, number, counts) -> {
            doIt(number, counts);
        });
        recyclerView.setAdapter(mAdapter);

    }
    int finder = 0;
    ArrayList<Integer> generic;
    public void doIt(int[] number, int counts){
        phNumber = new ArrayList<>();
        generic = new ArrayList<>();
        for (int i = 0;i<counts;i++){
            phNumber.add(number[i]);
        }

        for (int i = 1; i<=count;i++){
            for (int j = 0 ; j<counts;j++){
                if(i==number[j]){
                    finder++;
                }
            }
            if (finder==0){
                generic.add(i);
            }
            else{
                finder=0;
            }

        }
        Collections.shuffle(generic);
        phNumber.addAll(generic);
        mAdapter = new RandomAdapter(getApplicationContext(),phNumber, (v, numberss, countsss) -> {
            doIt(numberss, countsss);
        });
        recyclerView.setAdapter(mAdapter);

    }
}