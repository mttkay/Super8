package com.github.super8.fragments;

import roboguice.fragment.RoboFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.super8.R;
import com.github.super8.support.Fonts;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class InfoBoxFragment extends RoboFragment {

  public static final int CONTENT_WELCOME = 0;
  public static final int CONTENT_RECORD = 1;
  public static final int CONTENT_PLAY = 2;

  private ViewFlipper flipper;
  private Typeface font;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    font = Fonts.robotoThin(getActivity());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View layout = inflater.inflate(R.layout.info_box_fragment, container);

    flipper = (ViewFlipper) layout.findViewById(R.id.view_flipper);
    flipper.addView(inflater.inflate(R.layout.flipper_content_welcome, flipper, false));
    flipper.addView(inflater.inflate(R.layout.flipper_content_record, flipper, false));
    flipper.addView(inflater.inflate(R.layout.flipper_content_play, flipper, false));

    for (int i = 0; i < 3; i++) {
      View infoBox = flipper.getChildAt(i);
      TextView textView = (TextView) infoBox.findViewById(android.R.id.text1);
      textView.setTypeface(font);
      textView = (TextView) infoBox.findViewById(android.R.id.text2);
      textView.setTypeface(font, Typeface.BOLD);
    }

    return layout;
  }

  public void setContentView(int which) {
    flipper.setDisplayedChild(which);
  }

  public void animateContentView(final int child) {
    final View currentView = flipper.getCurrentView();
    final View nextView = flipper.getChildAt(child);
    TextView text1 = (TextView) currentView.findViewById(android.R.id.text1);
    TextView text2 = (TextView) currentView.findViewById(android.R.id.text2);

    AnimatorSet fadeAnim = new AnimatorSet();
    fadeAnim.playTogether(ObjectAnimator.ofFloat(text1, "alpha", 1, 0),
        ObjectAnimator.ofFloat(text2, "alpha", 1, 0));

    // AnimatorSet scaleAnim = new AnimatorSet();
    // scaleAnim.playTogether(ObjectAnimator.ofFloat(currentView, "scaleX", 1, 0.5f),
    // ObjectAnimator.ofFloat(currentView, "scaleY", 1, 0.5f));
    //
    // AnimatorSet flipAnim = new AnimatorSet();
    // flipAnim.playTogether(ObjectAnimator.ofFloat(currentView, "rotationX", 0, 90));
    //
    // AnimatorSet outAnim = new AnimatorSet();
    // outAnim.playSequentially(fadeAnim, scaleAnim, flipAnim);
    //
    // outAnim.setDuration(500);
    fadeAnim.setDuration(250);
    fadeAnim.start();

    fadeAnim.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        // currentView.setVisibility(View.GONE);
        TextView text1 = (TextView) nextView.findViewById(android.R.id.text1);
        TextView text2 = (TextView) nextView.findViewById(android.R.id.text2);

        AnimatorSet fadeAnim = new AnimatorSet();
        fadeAnim.playTogether(ObjectAnimator.ofFloat(text1, "alpha", 0, 1),
            ObjectAnimator.ofFloat(text2, "alpha", 0, 1));

        // AnimatorSet scaleAnim = new AnimatorSet();
        // scaleAnim.playTogether(ObjectAnimator.ofFloat(nextView, "scaleX", 0.5f, 1),
        // ObjectAnimator.ofFloat(nextView, "scaleY", 0.5f, 1));
        //
        // AnimatorSet flipAnim = new AnimatorSet();
        // flipAnim.playTogether(ObjectAnimator.ofFloat(nextView, "rotationX", 90, 0));
        //
        // AnimatorSet inAnim = new AnimatorSet();
        // inAnim.playSequentially(flipAnim, scaleAnim, fadeAnim);
        //
        // inAnim.setDuration(500);
        fadeAnim.setDuration(250);
        fadeAnim.start();
        flipper.setDisplayedChild(child);
      }
    });
    // scaleAnim.start();
  }
}
