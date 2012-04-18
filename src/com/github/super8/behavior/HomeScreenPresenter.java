package com.github.super8.behavior;

import com.github.super8.db.LibraryManager;
import com.google.inject.Inject;

public class HomeScreenPresenter implements Presenter<ActsAsHomeScreen> {

  private ActsAsHomeScreen homeScreen;
  private LibraryManager library;

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

  public void enterRecordingMode() {
    homeScreen.showSlidingDrawer();
    homeScreen.showRecordView();
  }

  public void enterPlaybackMode() {
    homeScreen.showSlidingDrawer();
    homeScreen.showPlayView();
  }

  private boolean watchlistNotEmpty() {
    return library.getWatchlistSize() > 0;
  }

  private boolean suggestionsAvailable() {
    return library.getSuggestionsCount() > 0;
  }
}
