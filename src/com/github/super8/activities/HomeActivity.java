package com.github.super8.activities;

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
import com.github.super8.fragments.ContentFragment;
import com.github.super8.fragments.LearnMoviesFragment;
import com.github.super8.support.SuperToast;

public class HomeActivity extends RoboFragmentActivity implements ActsAsHomeScreen {

  private HomeScreenPresenter presenter = new HomeScreenPresenter();

  @InjectView(R.id.drawer)
  private SlidingDrawer drawer;

  @InjectFragment(R.id.content_fragment)
  private ContentFragment contentFragment;

  @InjectFragment(R.id.learn_fragment)
  private LearnMoviesFragment learnFragment;
  
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
    
    SuperToast.info(this, R.string.help_text_first_start);
  }

  @Override
  public HomeScreenPresenter getPresenter() {
    return presenter;
  }
  
  @Override
  public void showSlidingDrawer() {
    drawer.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideSlidingDrawer() {
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
  public void showNoLikesView() {
    contentFragment.showWelcomeView();
  }
  
  @Override
  public void showLikeModeView() {
    contentFragment.showLikeModeView();
  }

  @Override
  public void showMoodView() {
    contentFragment.showMoodView();
  }
  
  public void onLikeModeButtonClicked(View view) {
    ToggleButton button = (ToggleButton) view;
    if (button.isChecked()) {
      presenter.likeMode();
    } else {
      presenter.welcomeMode();
    }
  }
}
