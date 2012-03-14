package com.github.super8.behavior;

import com.github.super8.Preferences;
import com.google.inject.Inject;

public class HomeScreenPresenter implements Presenter<ActsAsHomeScreen> {

  @Inject
  private Preferences preferences;

  private ActsAsHomeScreen homeScreen;

  @Override
  public void bind(ActsAsHomeScreen behavior) {
    this.homeScreen = behavior;

    welcomeMode();
  }

  public void welcomeMode() {
    if (noLikesYet()) {
      homeScreen.hideSlidingDrawer();
      homeScreen.showNoLikesView();
    } else {
      suggestionMode();
    }
  }

  public void likeMode() {
    homeScreen.showSlidingDrawer();
    homeScreen.showLikeModeView();
  }

  public void suggestionMode() {
    homeScreen.showSlidingDrawer();
    homeScreen.showMoodView();
  }

  private boolean noLikesYet() {
    return true;// FIXME
  }
}
