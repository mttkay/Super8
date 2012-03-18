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

  public void showWelcomeView() {
    flipper.setDisplayedChild(CONTENT_NO_LIKES);
  }

  public void showLikeModeView() {
    flipper.setDisplayedChild(CONTENT_LIKE_MODE);
  }

  public void showMoodView() {
    flipper.setDisplayedChild(CONTENT_SUGGESTION_MODE);
  }
}
