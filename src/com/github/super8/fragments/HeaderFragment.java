package com.github.super8.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.super8.R;
import com.github.super8.views.FlickerDrawable;

public class HeaderFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View fragment = inflater.inflate(R.layout.header_fragment, container);

    ImageView logoImage = (ImageView) fragment.findViewById(R.id.logo_image);
    logoImage.setImageDrawable(new FlickerDrawable(getResources().getDrawable(R.drawable.logo_off),
        getResources().getDrawable(R.drawable.logo_on)));

    return fragment;
  }

}
