package com.github.super8.behavior;

import com.github.super8.fragments.MovieDetailsFragment;

public interface ActsAsHomeScreen extends Behavior {

  void showSlidingDrawer();
  
  void hideSlidingDrawer();
  
  void openSlidingDrawer();
  
  void closeSlidingDrawer();
  
  void showWelcomeView(boolean firstTime);
  
  void showRecordView();
  
  void showPlayView();
  
  void showWatchlistView();
  
  HomeScreenPresenter getPresenter();

  MovieDetailsFragment getMovieDetailsFragment();
}
