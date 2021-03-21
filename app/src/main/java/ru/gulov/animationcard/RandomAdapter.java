package ru.gulov.animationcard;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import static java.lang.Math.abs;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {

    Context context;
    ArrayList<Integer> phNumber = new ArrayList<>();


    public MyAdapterListener onClickListener;


    public RandomAdapter(Context applicationContext,ArrayList<Integer> phNumber,  MyAdapterListener listener) {
        context = applicationContext;
        onClickListener = listener;
        this.phNumber = phNumber;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        EditText editText;
        public ViewHolder(View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.editText);
            textView = itemView.findViewById(R.id.textView);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    int count = 0;
    int[] savedNums = new int[150];
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView.setText("#"+(position+1)+" / "+phNumber.get(position));
        holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d("|plapla|", "backPressed: "+position+" | "+holder.editText.getText().toString());
                if (!holder.editText.getText().toString().equals("")){
                    count++;
                    savedNums[count-1] = Integer.parseInt(holder.editText.getText().toString());
                }
                else{
                    count = 0;
                    savedNums = new int[150];
                }
                onClickListener.itemClick(holder.editText, savedNums, count);
                return false;

            }

        });


    }

    @Override
    public int getItemCount() {
        return phNumber.size();
    }


    public interface MyAdapterListener {
        void itemClick(View v, int[] nums, int counts);
    }




}