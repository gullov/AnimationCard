package ru.gulov.animationcard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FragmentRandom extends Fragment {

     public FragmentRandom() {

    }
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Integer> phNumber = new ArrayList<>();
    Integer[] callDurations;
    int count;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_random, container, false);

        count =((MainActivity)getActivity()).count;
        callDurations = new Integer[count];
        phNumber = new ArrayList<>();
        for(int i = 0 ; i<count;i++){
            callDurations[i] = i+1;
            Log.d("TAGssss", "onCreate: "+callDurations[i]);
        }
        phNumber.addAll(Arrays.asList(callDurations));
        recyclerView = v.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.smoothScrollToPosition(0);
        mAdapter = new RandomAdapter(getActivity().getApplicationContext(),phNumber, (vs, number, counts) -> {
            doIt(number, counts);
        });
        recyclerView.setAdapter(mAdapter);

         return v;
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
        ((MainActivity)getActivity()).characters = phNumber;
        ((MainActivity)getActivity()).isShowNumbers = true;

        mAdapter = new RandomAdapter(getActivity().getApplicationContext(),phNumber, (v, numberss, countsss) -> {
            doIt(numberss, countsss);
        });
        recyclerView.setAdapter(mAdapter);

    }
}