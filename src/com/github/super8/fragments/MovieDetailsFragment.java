package com.github.super8.fragments;

import java.util.List;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.super8.R;
import com.github.super8.activities.HomeActivity.SuggestionLoadedHandler;
import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.github.super8.db.LibraryManager;
import com.github.super8.model.Movie;
import com.google.inject.Inject;

public class MovieDetailsFragment extends RoboFragment {

  @Inject private LibraryManager library;
  @Inject private TmdbApi tmdb;

  private int currentSuggestion = -1;
  private List<Movie> suggestions;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    suggestions = library.getMovieSuggestions();
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.movie_details_fragment, null);
  }

  public void loadNextSuggestion(SuggestionLoadedHandler suggestionLoadedHandler) {
    currentSuggestion = (currentSuggestion + 1) % suggestions.size();
    Movie movie = suggestions.get(currentSuggestion);
    tmdb.getMovie(suggestionLoadedHandler, movie.getTmdbId());
  }
}
