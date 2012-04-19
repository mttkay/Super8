package com.github.super8.behavior;

import android.widget.SlidingDrawer.OnDrawerCloseListener;

import com.github.super8.db.LibraryManager;
import com.github.super8.fragments.MovieDetailsFragment;
import com.google.inject.Inject;

public class HomeScreenPresenter implements Presenter<ActsAsHomeScreen>, OnDrawerCloseListener {

  public enum Mode {
    WELCOME, RECORD, PLAY
  }

  private ActsAsHomeScreen homeScreen;
  private LibraryManager library;
  private Mode mode = Mode.WELCOME;

  @Inject
  public HomeScreenPresenter(LibraryManager library) {
    this.library = library;
  }

  @Override
  public void bind(ActsAsHomeScreen behavior) {
    this.homeScreen = behavior;

    start(false);
  }

  public void start(boolean animateView) {
    if (watchlistNotEmpty()) {
      // populate watchlist
    } else if (suggestionsAvailable()) {
      enterPlaybackMode();
    } else {
      homeScreen.hideSlidingDrawer();
      homeScreen.showWelcomeView(animateView);
    }
  }

  public Mode getMode() {
    return mode;
  }

  public void enterRecordingMode() {
    homeScreen.showSlidingDrawer();
    homeScreen.showRecordView();
    mode = Mode.RECORD;
  }

  public void enterPlaybackMode() {
    homeScreen.showSlidingDrawer();
    homeScreen.showPlayView();
    mode = Mode.PLAY;
  }

  private boolean watchlistNotEmpty() {
    return library.getWatchlistSize() > 0;
  }

  private boolean suggestionsAvailable() {
    return library.getSuggestionsCount() > 0;
  }

  public void getNextMovieSuggestion() {
    homeScreen.hideSlidingDrawer();
    MovieDetailsFragment fragment = homeScreen.getMovieDetailsFragment();
    fragment.loadNextSuggestion();
  }

  public void onMovieSuggestionAvailable() {
    homeScreen.showSlidingDrawer();
    //homeScreen.openSlidingDrawer();
  }

  @Override
  public void onDrawerClosed() {
    if (mode == Mode.PLAY) {
      getNextMovieSuggestion();
    } else if (mode == Mode.RECORD && suggestionsAvailable()) {
      enterPlaybackMode();
    }
  }
}
