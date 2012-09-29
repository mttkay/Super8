package com.github.super8.fragments;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.github.super8.R;
import com.github.super8.behavior.ActsAsHomeScreen;
import com.github.super8.behavior.ControlPanel;
import com.github.super8.db.LibraryManager;
import com.github.super8.views.FlickerDrawable;
import com.google.inject.Inject;

public class HeaderFragment extends RoboFragment implements ControlPanel, OnClickListener {

  private Drawable logoDrawable;

  @Inject private LibraryManager library;

  @InjectView(R.id.header_logo) private ImageView logoImage;
  @InjectView(R.id.header_lens) private ImageView lensImage;
  @InjectView(R.id.header_camera_power_button) private ToggleButton powerButton;
  @InjectView(R.id.header_camera_play_button) private ToggleButton playButton;

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

    powerButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ActsAsHomeScreen activity = (ActsAsHomeScreen) getActivity();
        if (isChecked) {
          lensImage.setImageResource(R.drawable.lens_on);
          activity.getPresenter().powerOn();
        } else {
          lensImage.setImageResource(R.drawable.lens_off);
          activity.getPresenter().powerOff();
        }
      }
    });

    playButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    ActsAsHomeScreen activity = (ActsAsHomeScreen) getActivity();
    switch (v.getId()) {
    case R.id.header_camera_play_button:
      if (playButton.isChecked()) {
        activity.getPresenter().enterRecordingMode();
      } else {
        activity.getPresenter().enterPlaybackMode();
      }
      break;
    }
  }

  @Override
  public void showRecordView() {
    playButton.setEnabled(true);
    playButton.setChecked(true);
  }

  @Override
  public void hideRecordView() {
  }

  @Override
  public void showPlayView() {
    playButton.setEnabled(true);
    playButton.setChecked(false);
  }

  @Override
  public void hidePlayView() {
  }

  @Override
  public void disableControlPanel() {
    playButton.setChecked(false);
    playButton.setEnabled(false);
  }

  @Override
  public void enableControlPanel() {
    playButton.setEnabled(true);
  }
}
