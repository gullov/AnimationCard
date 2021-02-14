package ru.gulov.animationcard;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton v_trigger_fabs;
    CardView v_result_card;
    View background, backgrounds;
    TextView first, second, v_param_no_repeat_counter;
    int is = 0;
    int globalIndex = 0;
    AsyncTask<?, ?, ?> shuffle;
    ArrayList<Integer> number = new ArrayList<Integer>();
    ArrayList<Integer> helper100 = new ArrayList<Integer>();
    String counts = "";
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private boolean still = false;
    private boolean still2 = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgrounds = findViewById(R.id.backgrounds);
        background = findViewById(R.id.v_result_root);
        v_trigger_fabs = findViewById(R.id.v_trigger_fabs);

        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);

        loadAnimations();
        changeCameraDistance();


        backgrounds.setOnClickListener(v -> {
            circularReActivity();
            still2 = false;
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
                if (!still2) {
                    circularRevealActivity();
                    still2 = true;
                    RotateAnimation rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                    v_trigger_fabs.startAnimation(rotate);
                    flipCard(v_trigger_fabs);

                } else if (!still) {
                    RotateAnimation rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                    v_trigger_fabs.startAnimation(rotate);
                    flipCard(v_trigger_fabs);
                }
            }
        });


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

    }

    private void circularReActivity() {

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
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetRightOut.setInterpolator(new OvershootInterpolator(0.19f));

    }

    public void flipCard(View view) {
        if (!still) {
            if (!mIsBackVisible) {
                mSetRightOut.setTarget(mCardFrontLayout);
                mSetLeftIn.setTarget(mCardBackLayout);
                mSetRightOut.start();
                mSetLeftIn.start();
                mIsBackVisible = true;
            } else {
                mSetRightOut.setTarget(mCardBackLayout);
                mSetLeftIn.setTarget(mCardFrontLayout);
                mSetRightOut.start();
                mSetLeftIn.start();
                mIsBackVisible = false;
            }
        }
    }

}