package com.github.super8.behavior;

public interface ActsAsHomeScreen extends Behavior {

  void showSlidingDrawer();
  
  void hideSlidingDrawer();
  
  void openSlidingDrawer();
  
  void closeSlidingDrawer();
  
  void showNoLikesView(boolean firstTime);
  
  void showLikeModeView();
  
  void showMoodView();
  
  HomeScreenPresenter getPresenter();
  
}
