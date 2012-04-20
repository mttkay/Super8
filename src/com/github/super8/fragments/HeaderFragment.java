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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

  @InjectView(R.id.header_logo_image) private ImageView logoImage;
  @InjectView(R.id.header_camera_image) private ImageView cameraImage;
  @InjectView(R.id.header_camera_power_button) private ToggleButton powerButton;
  @InjectView(R.id.header_control_buttons) private RadioGroup controlButtons;
  @InjectView(R.id.header_camera_play_button) private RadioButton playButton;
  @InjectView(R.id.header_camera_rec_button) private RadioButton recButton;

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
          cameraImage.setImageResource(R.drawable.lens_on);
          activity.getPresenter().powerOn();
        } else {
          cameraImage.setImageResource(R.drawable.lens_off);
          activity.getPresenter().powerOff();
        }
      }
    });
    
    playButton.setOnClickListener(this);
    recButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    ActsAsHomeScreen activity = (ActsAsHomeScreen) getActivity();
    switch (v.getId()) {
    case R.id.header_camera_play_button:
      activity.getPresenter().enterPlaybackMode();
      break;
    case R.id.header_camera_rec_button:
      activity.getPresenter().enterRecordingMode();
      break;
    }
  }

  @Override
  public void showRecordView() {
    controlButtons.check(R.id.header_camera_rec_button);
    controlButtons.getChildAt(0).setEnabled(library.hasSuggestions());
  }

  @Override
  public void showPlayView() {
    controlButtons.check(R.id.header_camera_play_button);    
  }
  
  @Override
  public void disableControlPanel() {
    controlButtons.getChildAt(0).setEnabled(false);
    controlButtons.getChildAt(1).setEnabled(false);
    controlButtons.clearCheck();
  }
  
  @Override
  public void enableControlPanel() {
    controlButtons.getChildAt(0).setEnabled(true);
    controlButtons.getChildAt(1).setEnabled(true);
  }
}
