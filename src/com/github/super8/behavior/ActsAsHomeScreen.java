package com.github.super8.behavior;

import com.github.super8.fragments.MovieDetailsFragment;

public interface ActsAsHomeScreen extends Behavior, ControlPanel {

  void showSlidingDrawer();
  
  void hideSlidingDrawer();
  
  void openSlidingDrawer();
  
  void closeSlidingDrawer();
  
  void showWatchlistEmptyView();
    
  void showWatchlistView();
  
  HomeScreenPresenter getPresenter();

  MovieDetailsFragment getMovieDetailsFragment();
}
