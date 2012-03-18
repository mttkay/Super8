package com.github.super8.fragments;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.super8.R;
import com.github.super8.views.FlickerDrawable;

public class HeaderFragment extends RoboFragment {

  private Drawable logoDrawable;

  @InjectView(R.id.logo_image) private ImageView logoImage;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    logoDrawable = new FlickerDrawable(getResources().getDrawable(R.drawable.logo_off),
        getResources().getDrawable(R.drawable.logo_on));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.header_fragment, container);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    logoImage.setImageDrawable(logoDrawable);
  }

}
