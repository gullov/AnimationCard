package ru.gulov.animationcard;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Math.abs;

public class FragmentNum extends Fragment {


    FloatingActionButton v_trigger_fabs;
    View background, backgrounds;
    CustomEditText f_num_from_et, f_num_to, v_params_delay_et, v_params_quantity_et;
    TextView first, second, v_param_no_repeat_counter;
    public ArrayList<Integer> number = new ArrayList<Integer>();
    ArrayList<Integer> helper100 = new ArrayList<Integer>();
    String counts = "";
    ImageView v_param_no_repeat_menu;
    RelativeLayout card_front_color, card_back_color;
    ImageView v_param_add_features;
    int counter = 0;
    Switch switch2, switch3;
    CardView v_params_no_repeat, v_params_quantity, v_params_delay;
    TextView refreshButton;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ImageView v_params_delay_menu, v_param_quantity_menu;
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
    Boolean a = true;
    AlertDialog alertDialog;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private boolean still = false;
    private boolean firstShow = true;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private Switch switch1;
    public FragmentNum() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_num, container, false);
        v_param_add_features = v.findViewById(R.id.v_param_add_features);
        v_param_no_repeat_menu = v.findViewById(R.id.v_param_no_repeat_menu);
        v_param_quantity_menu = v.findViewById(R.id.v_param_quantity_menu);
        v_params_delay_menu = v.findViewById(R.id.v_params_delay_menu);
        pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        card_back_color = v.findViewById(R.id.back_card_color);
        card_front_color = v.findViewById(R.id.front_card_color);
        first = v.findViewById(R.id.first);
        second = v.findViewById(R.id.second);
        v_params_no_repeat = v.findViewById(R.id.v_params_no_repeat);
        v_params_quantity = v.findViewById(R.id.v_params_quantity);
        v_params_delay = v.findViewById(R.id.v_params_delay);

        f_num_from_et = v.findViewById(R.id.f_num_from_et);
        f_num_to = v.findViewById(R.id.f_num_to);
        v_params_delay_et = v.findViewById(R.id.v_params_delay_et);
        v_params_quantity_et = v.findViewById(R.id.v_params_quantity_et);

        v_param_no_repeat_counter = v.findViewById(R.id.v_param_no_repeat_counter);
        backgrounds = v.findViewById(R.id.backgrounds);
        background = v.findViewById(R.id.v_result_root);
        v_trigger_fabs = v.findViewById(R.id.v_trigger_fabs);


        mCardBackLayout = v.findViewById(R.id.card_back);
        mCardFrontLayout = v.findViewById(R.id.card_front);


        counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
        v_param_no_repeat_counter.setText("0/" + counts);

        f_num_to.setCustomListener(new CustomEditText.MyAdapterListener() {
            @Override
            public void backPressed(View v, boolean a) {

                f_num_to.clearFocus();
                hideKeyboard(v);
                Shuffling();
                counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
                v_param_no_repeat_counter.setText("0/" + counts);

            }
        });
        f_num_to.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    f_num_to.clearFocus();
                    hideKeyboard(v);
                    Shuffling();
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

        f_num_from_et.setCustomListener((v, a) -> {

            f_num_from_et.clearFocus();
            hideKeyboard(v);
            Shuffling();
            counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
            v_param_no_repeat_counter.setText("0/" + counts);
        });
        f_num_from_et.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    f_num_from_et.clearFocus();
                    hideKeyboard(v);
                    Shuffling();
                    counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
                    v_param_no_repeat_counter.setText("0/" + counts);
                    return true;
                default:
                    return false;
            }

        });
        f_num_from_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v_trigger_fabs.hide();
                } else {

                }
            }
        });

        v_params_delay_et.setCustomListener(new CustomEditText.MyAdapterListener() {
            @Override
            public void backPressed(View v, boolean a) {
                v_params_delay_et.clearFocus();
                hideKeyboard(v);
            }
        });
        v_params_delay_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {

                    case EditorInfo.IME_ACTION_DONE:
                        v_params_delay_et.clearFocus();
                        hideKeyboard(v);
                        return true;
                    default:
                        return false;
                }

            }

        });
        v_params_delay_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v_trigger_fabs.hide();
                } else {

                }
            }
        });

        v_params_quantity_et.setCustomListener(new CustomEditText.MyAdapterListener() {
            @Override
            public void backPressed(View v, boolean a) {
                v_params_quantity_et.clearFocus();
                hideKeyboard(v);

            }
        });
        v_params_quantity_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {

                    case EditorInfo.IME_ACTION_DONE:

                        v_params_quantity_et.clearFocus();
                        hideKeyboard(v);
                        return true;
                    default:
                        return false;
                }

            }

        });
        v_params_quantity_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v_trigger_fabs.hide();
                } else {


                }
            }
        });

        Shuffling();
        if (pref.getBoolean("s1", true)) {
            v_params_no_repeat.setVisibility(View.VISIBLE);
        } else {
            v_params_no_repeat.setVisibility(View.GONE);
        }
        if (pref.getBoolean("s2", true)) {
            v_params_quantity.setVisibility(View.VISIBLE);
        } else {
            v_params_quantity.setVisibility(View.GONE);
        }
        if (pref.getBoolean("s3", true)) {
            v_params_delay.setVisibility(View.VISIBLE);
        } else {
            v_params_delay.setVisibility(View.GONE);
        }


        backgrounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularReActivity();
                firstShow = true;
            }
        });

        v_trigger_fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).isShowNumbers) {
                    getNewData();
                    ((MainActivity) getActivity()).isShowNumbers = false;
                }
                if (firstShow) {
                    circularRevealActivity();
                    firstShow = false;
                    RotateAnimation rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                    v_trigger_fabs.startAnimation(rotate);
                    loadAnimations(number.get(counter));

                } else if (!still) {
                    RotateAnimation rotate = new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                    v_trigger_fabs.startAnimation(rotate);
                    loadAnimations(number.get(counter));
                }
            }
        });
        v_param_add_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.v_params_dialog, null);
                dialogBuilder.setView(dialogView);
                switch1 = dialogView.findViewById(R.id.switch122);
                switch2 = dialogView.findViewById(R.id.switch2);
                switch3 = dialogView.findViewById(R.id.switch3);
                switch1.setChecked(pref.getBoolean("s1", true));
                switch2.setChecked(pref.getBoolean("s2", true));
                switch3.setChecked(pref.getBoolean("s3", true));

                refreshButton = dialogView.findViewById(R.id.refreshButton);
                refreshButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (switch1.isChecked()) {
                            v_params_no_repeat.setVisibility(View.VISIBLE);
                            editor.putBoolean("s1", true);
                        } else {
                            v_params_no_repeat.setVisibility(View.GONE);
                            editor.putBoolean("s1", false);
                        }
                        if (switch2.isChecked()) {
                            v_params_quantity.setVisibility(View.VISIBLE);
                            editor.putBoolean("s2", true);
                        } else {
                            v_params_quantity.setVisibility(View.GONE);

                            editor.putBoolean("s2", false);
                        }
                        if (switch3.isChecked()) {
                            v_params_delay.setVisibility(View.VISIBLE);

                            editor.putBoolean("s3", true);
                        } else {
                            v_params_delay.setVisibility(View.GONE);

                            editor.putBoolean("s3", false);
                        }
                        editor.apply();
                        alertDialog.hide();
                    }
                });


                alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
        v_param_add_features.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((MainActivity) getActivity()).count = number.size();
                ((MainActivity) getActivity()).ShowRandom();

                return true;
            }
        });
        v_param_no_repeat_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v_param_no_repeat_menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.pup2, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.two) {
                            v_params_no_repeat.setVisibility(View.GONE);
                            editor.putBoolean("s1", false);
                            editor.apply();

                        }
                        else {

                            f_num_to.clearFocus();
                            hideKeyboard(v);
                            Shuffling();
                            counts = "" + (abs(Integer.parseInt(f_num_to.getText().toString()) - Integer.parseInt(f_num_from_et.getText().toString())) + 1);
                            v_param_no_repeat_counter.setText("0/" + counts);

                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
        v_params_delay_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v_params_delay_menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.pup3, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        v_params_delay.setVisibility(View.GONE);
                        editor.putBoolean("s3", false);

                        editor.apply();

                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });
        v_param_quantity_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), v_param_quantity_menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.pup3, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        v_params_quantity.setVisibility(View.GONE);
                        editor.putBoolean("s2", false);

                        editor.apply();
                        return true;
                    }
                });

                popup.show(); //showing popup menu
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
        ((MainActivity) getActivity()).isShowed = true;

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

        ((MainActivity) getActivity()).isShowed = true;
        firstShow = true;

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }


    private void loadAnimations(int number) {
        int handlerTime = 0;
        if (v_params_delay_et.getText().toString().equals("1")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom1);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            handlerTime = 300;
        } else if (v_params_delay_et.getText().toString().equals("2")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom2);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 1300;

        } else if (v_params_delay_et.getText().toString().equals("3")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom3);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 2500;

        } else if (v_params_delay_et.getText().toString().equals("4")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom4);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 3700;

        } else if (v_params_delay_et.getText().toString().equals("5")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom5);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 4900;

        } else if (v_params_delay_et.getText().toString().equals("6")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom6);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 6100;
        } else if (v_params_delay_et.getText().toString().equals("7")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom7);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 7200;

        } else if (v_params_delay_et.getText().toString().equals("8")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom8);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 8500;

        } else if (v_params_delay_et.getText().toString().equals("9")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom9);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 9700;


        } else if (v_params_delay_et.getText().toString().equals("10")) {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom10);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 9900;

        } else {
            mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.in_animation_custom1);
            mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.out_animation);
            mSetRightOut.setInterpolator(new DecelerateInterpolator(2f));
            handlerTime = 0;

        }
        int distance = 1400;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
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

        flipCard(number, handlerTime);

    }

    int count = 1;
    public void flipCard(int number, int mills) {
        Log.d("plaplaplalaplpalpalpl", "flipCard: "+counter);
        if(!v_params_quantity_et.getText().toString().equals("")){
            count = Integer.parseInt(v_params_quantity_et.getText().toString());
        }
        else{
            count = 1;
        }
        if (!still) {
            if (!mIsBackVisible) {
                first.setText("");
                second.setText("");
                mSetRightOut.setTarget(mCardBackLayout);
                mSetLeftIn.setTarget(mCardFrontLayout);
                mSetRightOut.start();
                StringBuilder s = new StringBuilder();

                if(count+counter<this.number.size()) {
                    for (int i = 0; i <count; i++) {
                        if (i  == count-1) {
                            s.append(this.number.get(counter + i));
                            break;
                        } else {
                            s.append(this.number.get(counter + i)).append(", ");
                        }
                    }
                    counter += count;
                }
                else{
                    for (int i = counter; i < this.number.size(); i++) {
                        if(i+1==this.number.size()){
                            s.append(this.number.get(counter + i));
                            break;
                        }
                        else  s.append(this.number.get(i)).append(", ");
                        showSnacbar();
                    }
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (count > 1) {
                            second.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            second.setText(s);
                        } else {
                            second.setText(number + "");
                            second.setTextSize(TypedValue.COMPLEX_UNIT_SP, 95);
                        }

                        Handler handleedr = new Handler();
                        handleedr.postDelayed(new Runnable() {
                            public void run() {
                                first.setText("");
                                mSetLeftIn.start();
                            }
                        }, 450);
                    }
                }, mills);


                mIsBackVisible = true;
                final int min = 0;
                final int max = 8;
                final int random = new Random().nextInt((max - min) + 1) + min;
                card_front_color.setBackgroundTintList(getResources().getColorStateList(colors[random]));

            } else {

                first.setText("");
                second.setText("");
                mSetRightOut.setTarget(mCardFrontLayout);
                mSetLeftIn.setTarget(mCardBackLayout);
                mSetRightOut.start();

                StringBuilder s = new StringBuilder();
                if(count+counter<this.number.size()) {
                    for (int i = 0; i <count; i++) {
                        if (i  == count-1) {
                            s.append(this.number.get(counter + i));
                            break;
                        } else {
                            s.append(this.number.get(counter + i)).append(", ");
                        }
                    }
                    counter += count;
                }
                else{
                    for (int i = counter; i < this.number.size(); i++) {
                        if(i+1==this.number.size()){
                            s.append(this.number.get(i));
                            break;
                        }
                        else  s.append(this.number.get(i)).append(", ");

                        showSnacbar();
                    }
                }


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if (count > 1) {
                            first.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                            first.setText(s);
                        } else {
                            first.setText(number + "");
                            first.setTextSize(TypedValue.COMPLEX_UNIT_SP, 95);
                        }
                        Handler handleedr = new Handler();
                        handleedr.postDelayed(new Runnable() {
                            public void run() {
                                second.setText("0");
                                mSetLeftIn.start();
                            }
                        }, 450);
                    }
                }, mills);


                mIsBackVisible = false;
                final int min = 0;
                final int max = 8;
                final int random = new Random().nextInt((max - min) + 1) + min;
                card_back_color.setBackgroundTintList(getResources().getColorStateList(colors[random]));
            }

            if (counter == this.number.size() - 1) {

            }


            v_param_no_repeat_counter.setText(counter + "/" + counts);


        }




    }
    public void showSnacbar(){
        counter = 0;
        Shuffling();
        Snackbar.make(v.findViewById(R.id.root), "Все возможные варианты сгенерированы!", Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    public void Shuffling() {
        number = new ArrayList<>();
        for (int i = Integer.parseInt(f_num_from_et.getText().toString()) - 1;
             i < Integer.parseInt(f_num_to.getText().toString()); i++) {
            number.add((int) (i + 1));
        }
        Collections.shuffle(number);
    }

    public void getNewData() {
        number = ((MainActivity) getActivity()).characters;
    }

    @Override
    public void onPause() {
        editor.putString("s4", f_num_from_et.getText().toString());
        editor.putString("s5", f_num_to.getText().toString());
        editor.putString("s6", v_params_delay_et.getText().toString());
        editor.putString("s7", v_params_quantity_et.getText().toString());
        editor.putInt("s8", counter);
        editor.apply();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
