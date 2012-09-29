package com.github.super8.activities;

import roboguice.inject.InjectFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.Window;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.github.super8.R;
import com.github.super8.behavior.ActsAsHomeScreen;
import com.github.super8.behavior.HomeScreenPresenter;
import com.github.super8.behavior.RecordPanelsDirector;
import com.github.super8.fragments.HeaderFragment;
import com.github.super8.fragments.WatchlistFragment;
import com.github.super8.gestures.ShakeDetector;
import com.github.super8.gestures.ShakeDetector.OnShakeListener;
import com.github.super8.services.ImportFilmographyHandler;
import com.google.inject.Inject;

public class HomeActivity extends RoboSherlockFragmentActivity implements ActsAsHomeScreen,
    OnShakeListener {

  @Inject private HomeScreenPresenter presenter;
  @Inject private RecordPanelsDirector recordPanelsDirector;

  @InjectFragment(R.id.header_fragment) private HeaderFragment headerFragment;
  @InjectFragment(R.id.watchlist_fragment) private WatchlistFragment watchlistFragment;

  @InjectView(R.id.play_mode_layout) private ViewGroup playPanelsContainer;

  private ShakeDetector shakeDetector;
  private ImportFilmographyHandler importHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    requestWindowFeature(Window.FEATURE_PROGRESS);

    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowHomeEnabled(false);
    actionBar.setDisplayShowTitleEnabled(true);
    actionBar.hide();

    shakeDetector = new ShakeDetector();
    shakeDetector.setOnShakeListener(this);

    setContentView(R.layout.home);

    presenter.bind(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    SensorManager sensors = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    sensors.registerListener(shakeDetector, sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_UI);
  }

  @Override
  protected void onPause() {
    super.onPause();
    SensorManager sensors = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    sensors.unregisterListener(shakeDetector);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (importHandler != null) {
      importHandler.detach();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // TODO Auto-generated method stub
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public HomeScreenPresenter getPresenter() {
    return presenter;
  }

  @Override
  public ImportFilmographyHandler getImportFilmographyHandler() {
    if (importHandler == null) {
      importHandler = new ImportFilmographyHandler(this);
    }
    return importHandler;
  }

  @Override
  public void showWatchlist() {
    watchlistFragment.show(true);
  }

  @Override
  public void hideWatchlist() {
    watchlistFragment.show(false);
  }

  @Override
  public void showRecordView() {
    headerFragment.showRecordView();
    recordPanelsDirector.show(true);
  }

  @Override
  public void hideRecordView() {
    recordPanelsDirector.show(false);
  }

  @Override
  public void showPlayView() {
    headerFragment.showPlayView();
    playPanelsContainer.setVisibility(View.VISIBLE);
  }

  @Override
  public void hidePlayView() {
    playPanelsContainer.setVisibility(View.GONE);
  }

  @Override
  public void loadMovieSuggestion() {
    // TODO: needs porting
    // hideSlidingDrawer();
    // movieDetailsFragment.loadNextSuggestion();
  }

  // public void onLikeModeButtonClicked(View view) {
  // ToggleButton button = (ToggleButton) view;
  // AnimatorSet anim = new AnimatorSet();
  // anim.playTogether(ObjectAnimator.ofFloat(button, "scaleX", 1, 1.3f, 1),
  // ObjectAnimator.ofFloat(button, "scaleY", 1, 1.2f, 1));
  // anim.setDuration(500);
  // anim.start();
  // if (button.isChecked()) {
  // presenter.enterRecordingMode();
  // } else {
  // presenter.powerOff();
  // }
  // }

  @Override
  public void onBackPressed() {
    boolean handled = false;
    switch (presenter.getState()) {
    case RECORD:
      handled = recordPanelsDirector.onBackPressed();
      break;
    case PLAY:
      break;
    }
    if (!handled) {
      super.onBackPressed();
    }
  }

  @Override
  public void onShake() {
    // TODO: needs porting
    // closeSlidingDrawer();
  }

  @Override
  public void disableControlPanel() {
    headerFragment.disableControlPanel();
  }

  @Override
  public void enableControlPanel() {
    headerFragment.enableControlPanel();
  }
}
