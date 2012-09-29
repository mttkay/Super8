package com.github.super8.behavior;

import android.widget.SlidingDrawer.OnDrawerCloseListener;

import com.github.super8.db.LibraryManager;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HomeScreenPresenter implements Presenter<ActsAsHomeScreen>, OnDrawerCloseListener {

  public enum State {
    OFF, RECORD, PLAY;
  }

  private ActsAsHomeScreen homeScreen;
  private LibraryManager library;
  private State state = State.OFF;

  @Inject
  public HomeScreenPresenter(LibraryManager library) {
    this.library = library;
  }

  @Override
  public void bind(ActsAsHomeScreen behavior) {
    this.homeScreen = behavior;

    powerOff();
  }

  public void powerOff() {
    homeScreen.disableControlPanel();
    homeScreen.hidePlayView();
    homeScreen.hideRecordView();
    homeScreen.showWatchlist();
    state = State.OFF;
  }

  public void powerOn() {
    homeScreen.hideWatchlist();
    homeScreen.enableControlPanel();
    if (library.hasSuggestions()) {
      enterPlaybackMode();
    } else {
      enterRecordingMode();
    }
  }

  public State getState() {
    return state;
  }

  public void enterRecordingMode() {
    homeScreen.hidePlayView();
    homeScreen.showRecordView();
    state = State.RECORD;
  }

  public void enterPlaybackMode() {
    homeScreen.hideRecordView();
    homeScreen.showPlayView();
    homeScreen.loadMovieSuggestion();
    state = State.PLAY;
  }

  public void getNextMovieSuggestion() {
//    if (state != State.PLAY) {
//      enterPlaybackMode();
//    } else {
//      homeScreen.closeSlidingDrawer(); // this will trigger the next suggestion
//    }
  }

  public void onMovieSuggestionAvailable() {
    //homeScreen.showSlidingDrawer();
    // homeScreen.openSlidingDrawer();
  }

  @Override
  public void onDrawerClosed() {
    if (library.hasSuggestions()) {
      if (state == State.PLAY) {
        homeScreen.loadMovieSuggestion();
      } else if (state == State.RECORD) {
        enterPlaybackMode();
      }
    }
  }
}
