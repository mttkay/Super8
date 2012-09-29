package com.github.super8.fragments;

import java.util.List;

import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.github.ignition.core.widgets.RemoteImageView;
import com.github.super8.R;
import com.github.super8.activities.MovieDetailsActivity;
import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.github.super8.apis.tmdb.v3.TmdbApiHandler;
import com.github.super8.behavior.ActsAsHomeScreen;
import com.github.super8.behavior.HomeScreenPresenter;
import com.github.super8.db.LibraryManager;
import com.github.super8.model.Movie;
import com.github.super8.support.Fonts;
import com.github.super8.support.SuperToast;
import com.google.inject.Inject;

//TODO: Need to rewrite this to be in a ViewPager
public class MovieSuggestionFragment extends TaskManagingFragment<Movie> implements
    TmdbApiHandler<Movie>, OnClickListener, ActionMode.Callback {

  private static final int TASK_GET_MOVIE = 0;

  @Inject private HomeScreenPresenter presenter;
  @Inject private LibraryManager library;
  @Inject private TmdbApi tmdb;
  @InjectView(R.id.movie_compact_layout) private View layout;
  @InjectView(R.id.movie_compact_title) private TextView movieTitle;
  @InjectView(R.id.movie_compact_poster) private RemoteImageView moviePoster;

  private ActionMode actionMode;
  private int currentSuggestion = -1;
  private List<Movie> suggestions;
  private Movie movie;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    suggestions = library.getMovieSuggestions();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.movie_compact, null);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    layout.setOnClickListener(this);
    movieTitle.setTypeface(Fonts.robotoThin(getActivity()));
  }

  public void loadNextSuggestion() {
    if (!suggestions.isEmpty()) {
      currentSuggestion = (currentSuggestion + 1) % suggestions.size();
      movie = suggestions.get(currentSuggestion);
      addTask(TASK_GET_MOVIE, tmdb.backfillMovie(this, movie));
    }
  }

  @Override
  public boolean onTaskSuccess(Context context, Movie movie) {
    String imageUrl = movie.getScaledImageUrl(context);
    if (imageUrl != null) {
      moviePoster.setImageUrl(imageUrl);
      moviePoster.loadImage();
      movieTitle.setVisibility(View.GONE);
    } else {
      // TODO: show dummy image
      movieTitle.setText(movie.getTitle());
      movieTitle.setVisibility(View.VISIBLE);
    }

    ActsAsHomeScreen homeScreen = (ActsAsHomeScreen) getActivity();
    homeScreen.getPresenter().onMovieSuggestionAvailable();

    return true;
  }

  @Override
  public void onClick(View v) {
    SherlockFragmentActivity activity = (SherlockFragmentActivity) getActivity();
    if (actionMode != null) {
      stopActionMode();
    } else {
      actionMode = activity.startActionMode(this);
    }
  }

  private void stopActionMode() {
    actionMode.finish();
    actionMode = null;
  }

  @Override
  public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    stopActionMode();

    switch (item.getItemId()) {
    case R.id.menu_watchlist_details:
      MovieDetailsActivity.launch(getActivity(), movie);
      break;
    case R.id.menu_suggestion_watchlist_add:
      library.addToWatchlist(movie);
      presenter.getNextMovieSuggestion();
      break;
    case R.id.menu_suggestion_seen:
      library.markAsSeen(movie);
      presenter.getNextMovieSuggestion();
      break;
    case R.id.menu_suggestion_hate:
      SuperToast.TODO(getActivity());
      presenter.getNextMovieSuggestion();
      break;
    }

    return true;
  }

  @Override
  public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    int defaultFlags = MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT;
    menu.add(Menu.NONE, R.id.menu_watchlist_details, Menu.NONE, R.string.menu_watchlist_details)
        .setIcon(android.R.drawable.ic_menu_info_details).setShowAsAction(defaultFlags);
    menu.add(Menu.NONE, R.id.menu_suggestion_watchlist_add, Menu.NONE,
        R.string.menu_suggestion_watchlist_add).setIcon(android.R.drawable.ic_menu_add)
        .setShowAsAction(defaultFlags);
    menu.add(Menu.NONE, R.id.menu_suggestion_seen, Menu.NONE, R.string.menu_suggestion_seen)
        .setIcon(android.R.drawable.ic_menu_view).setShowAsAction(defaultFlags);
    menu.add(Menu.NONE, R.id.menu_suggestion_hate, Menu.NONE, R.string.menu_suggestion_hate)
        .setIcon(android.R.drawable.ic_menu_close_clear_cancel).setShowAsAction(defaultFlags);
    return true;
  }

  @Override
  public void onDestroyActionMode(ActionMode mode) {
  }

  @Override
  public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
    mode.setTitle(movie.getTitle());
    return true;
  }
}
