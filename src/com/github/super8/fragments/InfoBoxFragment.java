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

  private static final int CONTENT_NO_LIKES = 0;
  private static final int CONTENT_LIKE_MODE = 1;
  private static final int CONTENT_SUGGESTION_MODE = 2;

  private ViewFlipper flipper;
  private Typeface font;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    font = Fonts.loadFont(getActivity(), "AYearWithoutRain.ttf");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    View layout = inflater.inflate(R.layout.info_box_fragment, container);

    flipper = (ViewFlipper) layout.findViewById(R.id.view_flipper);
    flipper.addView(inflater.inflate(R.layout.flipper_content_no_likes, flipper, false));
    flipper.addView(inflater.inflate(R.layout.flipper_content_like_mode, flipper, false));
    flipper.addView(inflater.inflate(R.layout.flipper_content_mood_icons, flipper, false));

    for (int i = 0; i < 2; i++) {
      View infoBox = flipper.getChildAt(i);
      TextView textView = (TextView) infoBox.findViewById(android.R.id.text1);
      textView.setTypeface(font);
      textView = (TextView) infoBox.findViewById(android.R.id.text2);
      textView.setTypeface(font);
    }

    return layout;
  }

  public void showWelcomeView(boolean firstTime) {
    if (firstTime) {
      flipper.setDisplayedChild(CONTENT_NO_LIKES);
    } else {
      animateInfoView(CONTENT_NO_LIKES);
    }
  }

  public void showLikeModeView() {
    animateInfoView(CONTENT_LIKE_MODE);
  }

  public void showMoodView() {
    animateInfoView(CONTENT_SUGGESTION_MODE);
  }

  private void animateInfoView(final int child) {
    final View currentView = flipper.getCurrentView();
    final View nextView = flipper.getChildAt(child);

    AnimatorSet scaleAnim = new AnimatorSet();
    scaleAnim.playTogether(ObjectAnimator.ofFloat(currentView, "scaleX", 1, 0.5f),
        ObjectAnimator.ofFloat(currentView, "scaleY", 1, 0.5f));

    AnimatorSet flipAnim = new AnimatorSet();
    flipAnim.playTogether(ObjectAnimator.ofFloat(currentView, "rotationX", 0, 90));

    AnimatorSet outAnim = new AnimatorSet();
    outAnim.playSequentially(scaleAnim, flipAnim);

    outAnim.setDuration(500);
    outAnim.start();

    outAnim.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationEnd(Animator animation) {
        //currentView.setVisibility(View.GONE);

        AnimatorSet scaleAnim = new AnimatorSet();
        scaleAnim.playTogether(ObjectAnimator.ofFloat(nextView, "scaleX", 0.5f, 1),
            ObjectAnimator.ofFloat(nextView, "scaleY", 0.5f, 1));

        AnimatorSet flipAnim = new AnimatorSet();
        flipAnim.playTogether(ObjectAnimator.ofFloat(nextView, "rotationX", 90, 0));

        AnimatorSet inAnim = new AnimatorSet();
        inAnim.playSequentially(flipAnim, scaleAnim);

        inAnim.setDuration(500);
        inAnim.start();
        flipper.setDisplayedChild(child);
      }
    });
    // scaleAnim.start();
  }
}
