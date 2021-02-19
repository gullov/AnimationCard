package ru.gulov.animationcard;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static java.lang.Math.abs;

public class FragmentNum extends Fragment {


    FloatingActionButton v_trigger_fabs;
    CardView v_result_card;
    View background, backgrounds;
    CustomEditText f_num_from_et, f_num_to;
    TextView first, second, v_param_no_repeat_counter;
    int is = 0;
    int globalIndex = 0;
    AsyncTask<?, ?, ?> shuffle;
    ArrayList<Integer> number = new ArrayList<Integer>();
    ArrayList<Integer> helper100 = new ArrayList<Integer>();
    String counts = "";
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private AnimatorSet mHide;
    private AnimatorSet mShow;
    private boolean mIsBackVisible = false;
    private boolean still = false;
    private boolean firstShow = true;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    int counter = 0;
    public FragmentNum() {
        // Required empty public constructor
    }
    int[] colors = {R.color.n1,
            R.color.n2,
            R.color.n3,
            R.color.n4,
            R.color.n5,
            R.color.n6,
            R.color.n7,
            R.color.n8,
            R.color.n9,
    };
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_num, container, false);


        first = v.findViewById(R.id.first);
        second = v.findViewById(R.id.second);
        f_num_from_et = v.findViewById(R.id.f_num_from_et);
        f_num_to = v.findViewById(R.id.f_num_to);
        v_param_no_repeat_counter = v.findViewById(R.id.v_param_no_repeat_counter);
        backgrounds = v.findViewById(R.id.backgrounds);
        background = v.findViewById(R.id.v_result_root);
        v_trigger_fabs = v.findViewById(R.id.v_trigger_fabs);


        mCardBackLayout = v.findViewById(R.id.card_back);
        mCardFrontLayout = v.findViewById(R.id.card_front);


        loadAnimations();
        changeCameraDistance();

        counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
        v_param_no_repeat_counter.setText("0/" + counts);

        f_num_to.setCustomListener(new CustomEditText.MyAdapterListener() {
            @Override
            public void backPressed(View v, boolean a) {
                Log.d("plaplapl", "onEditorAction: " + "plaplap");
                f_num_to.clearFocus();
                hideKeyboard(v);
                new Shuffle().execute();
                counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
                v_param_no_repeat_counter.setText("0/" + counts);

            }
        });
        f_num_to.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d("plaplapl", "onEditorAction: " + "plaplap");
                    f_num_to.clearFocus();
                    hideKeyboard(v);
                    new Shuffle().execute();
                    counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
                    v_param_no_repeat_counter.setText("0/" + counts);
                    return true;
                }
                return false;

            }

        });
        f_num_to.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v_trigger_fabs.hide();
                } else {
                    //lost focus
                }
            }
        });

        f_num_from_et.setCustomListener(new CustomEditText.MyAdapterListener() {
            @Override
            public void backPressed(View v, boolean a) {
                Log.d("plaplapl", "onEditorAction: " + "plaplap");
                f_num_from_et.clearFocus();
                hideKeyboard(v);
                new Shuffle().execute();

                counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
                v_param_no_repeat_counter.setText("0/" + counts);
            }
        });
        f_num_from_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 switch (actionId) {

                    case EditorInfo.IME_ACTION_DONE:
                        Log.d("plaplapl", "onEditorAction: " + "plaplap");
                        f_num_from_et.clearFocus();
                        hideKeyboard(v);
                        new Shuffle().execute();
                        counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
                        v_param_no_repeat_counter.setText("0/" + counts);
                        return true;
                    default:
                        return false;
                }

            }

        });
        f_num_from_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v_trigger_fabs.hide();
                } else {
                    //lost focus
                }
            }
        });


        new Shuffle().execute();



        backgrounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularReActivity();
                firstShow = true;
            }
        });
        mSetLeftIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                still = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                still = true;
            }
        });
        v_trigger_fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstShow) {
                    circularRevealActivity();
                    firstShow = false;
                    RotateAnimation rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                    v_trigger_fabs.startAnimation(rotate);
                    flipCard(number.get(counter));

                }
                else if (!still) {
                    RotateAnimation rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                    v_trigger_fabs.startAnimation(rotate);
                    flipCard(number.get(counter));
                }
            }
        });

        return v;

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        v_trigger_fabs.show();
        counter = 0;
    }

    private void circularRevealActivity() {
        int cx = background.getRight() - getDips(50);
        int cy = background.getBottom() - getDips(50);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,
                cx,
                cy,
                0,
                finalRadius);

        circularReveal.setDuration(300);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
        background.setVisibility(View.VISIBLE);
        circularReveal.start();
        ((MainActivity)getActivity()).isShowed = true;

    }

    public void circularReActivity() {

        int cx = background.getRight() - getDips(50);
        int cy = background.getBottom() - getDips(50);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);

        circularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                background.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        circularReveal.setDuration(300);
        circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
        circularReveal.start();

        ((MainActivity)getActivity()).isShowed = true;
        firstShow = true;

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    private void changeCameraDistance() {
        int distance = 1400;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
        mSetRightOut.setInterpolator(new OvershootInterpolator(0.19f));

        mSetRightOut.setTarget(mCardFrontLayout);
        mSetLeftIn.setTarget(mCardBackLayout);
        mSetRightOut.start();
        mSetLeftIn.start();


    }


    public void flipCard(int number) {
        if (!still) {
            if (!mIsBackVisible) {
                first.setText(number+"");
                mSetRightOut.setTarget(mCardFrontLayout);
                mSetLeftIn.setTarget(mCardBackLayout);
                mSetRightOut.start();
                mSetLeftIn.start();
                mIsBackVisible = true;
            } else {
                second.setText(number+"");
                mSetRightOut.setTarget(mCardBackLayout);
                mSetLeftIn.setTarget(mCardFrontLayout);
                mSetRightOut.start();
                mSetLeftIn.start();
                mIsBackVisible = false;
            }
        }
        Log.d("plaplaplpal", "flipCard: "+counter+"  "+this.number.size());
        if (counter==this.number.size()-1){
            counter=0;
            Snackbar.make(v.findViewById(R.id.root), "Все возможные варианты сгенерированы!", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
        }
        counter++;
        v_param_no_repeat_counter.setText(counter+"/" + counts);

    }

    class Shuffle extends AsyncTask<Void, Void, Void> {
        int counter = 0;
        int counter2 = 100;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            number = new ArrayList<Integer>();
            helper100 = new ArrayList<Integer>();
        }

        @Override
        protected Void doInBackground(Void... params) {

            for (int i = Integer.parseInt(f_num_from_et.getText().toString()) - 1;
                 i < Integer.parseInt(f_num_to.getText().toString()); i++) {
                number.add((int) (i + 1));
                counter++;
                Log.d("plaplapla", "doInBackground: " + number.get(counter - 1) + "  " + number.size());
            }
            Collections.shuffle(number);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            Log.d("TAGssss", "onPostExecute: ");
        }

    }
}
