package com.github.super8.behavior;

import com.github.super8.services.ImportFilmographyHandler;

public interface ActsAsHomeScreen extends Behavior, ControlPanel {

  void showWatchlist();

  void hideWatchlist();
  
  void loadMovieSuggestion();

  HomeScreenPresenter getPresenter();

  ImportFilmographyHandler getImportFilmographyHandler();
}
