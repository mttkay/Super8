package com.github.super8.activities;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectFragment;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.ToggleButton;

import com.github.super8.R;
import com.github.super8.behavior.ActsAsHomeScreen;
import com.github.super8.behavior.HomeScreenPresenter;
import com.github.super8.fragments.InfoBoxFragment;
import com.github.super8.fragments.LearnMoviesFragment;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

public class HomeActivity extends RoboFragmentActivity implements ActsAsHomeScreen {

  private HomeScreenPresenter presenter = new HomeScreenPresenter();

  @InjectView(R.id.drawer) private SlidingDrawer drawer;

  @InjectFragment(R.id.infobox_fragment) private InfoBoxFragment infoboxFragment;

  @InjectFragment(R.id.learn_fragment) private LearnMoviesFragment learnFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.home);

    presenter.bind(this);

    drawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {

      @Override
      public void onDrawerOpened() {

      }
    });
  }

  @Override
  public HomeScreenPresenter getPresenter() {
    return presenter;
  }

  @Override
  public void showSlidingDrawer() {
    animate(drawer).alpha(1.0f).setDuration(500).start();
    drawer.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideSlidingDrawer() {
    animate(drawer).alpha(0).setDuration(500).start();
    drawer.setVisibility(View.GONE);
  }

  @Override
  public void openSlidingDrawer() {
    drawer.animateOpen();
  }

  @Override
  public void closeSlidingDrawer() {
    drawer.animateClose();
  }

  @Override
  public void showNoLikesView(boolean firstTime) {
    infoboxFragment.showWelcomeView(firstTime);
  }

  @Override
  public void showLikeModeView() {
    infoboxFragment.showLikeModeView();
  }

  @Override
  public void showMoodView() {
    infoboxFragment.showMoodView();
  }

  public void onLikeModeButtonClicked(View view) {
    ToggleButton button = (ToggleButton) view;
    ValueAnimator anim = ObjectAnimator.ofFloat(button, "scaleX", 1, 1.25f, 1);
    anim.setDuration(500);
    anim.start();
    if (button.isChecked()) {
      presenter.likeMode();
    } else {
      presenter.welcomeMode(false);
    }
  }
  
  @Override
  public void onBackPressed() {
    if (drawer.isOpened()) {
      closeSlidingDrawer();
    } else {
      super.onBackPressed();
    }
  }
}
