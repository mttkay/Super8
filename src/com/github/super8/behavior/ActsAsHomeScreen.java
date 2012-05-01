package com.github.super8.behavior;

import com.github.super8.services.ImportFilmographyHandler;

public interface ActsAsHomeScreen extends Behavior, ControlPanel {

  void showSlidingDrawer();

  void hideSlidingDrawer();

  void openSlidingDrawer();

  void closeSlidingDrawer();

  void showWatchlistEmptyView();

  void showWatchlistView();

  void loadMovieSuggestion();

  HomeScreenPresenter getPresenter();

  ImportFilmographyHandler getImportFilmographyHandler();
}
