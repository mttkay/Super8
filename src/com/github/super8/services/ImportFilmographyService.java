package com.github.super8.services;

import roboguice.service.RoboIntentService;
import android.content.Intent;

import com.github.super8.apis.ServerCommunicationException;
import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.github.super8.apis.tmdb.v3.TmdbError;
import com.github.super8.db.LibraryManager;
import com.github.super8.model.Appearance;
import com.github.super8.model.Credits;
import com.github.super8.model.Movie;
import com.google.inject.Inject;

public class ImportFilmographyService extends RoboIntentService {

  public static final String PERSON_ID_EXTRA = "pid";

  @Inject private TmdbApi tmdb;
  @Inject private LibraryManager library;

  public ImportFilmographyService() {
    super(ImportFilmographyService.class.getSimpleName());
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    int personId = intent.getIntExtra(PERSON_ID_EXTRA, 0);
    try {
      Credits credits = tmdb.getCredits(personId);

      System.out.println("New credits: " + credits.getTmdbId());
      for (Appearance app : credits.getAllAppearances()) {
        System.out.println(app.getMovieTitle());
        // fetch the full movie record for this appearance
        Movie movie = tmdb.getMovie(app.getTmdbId());
        System.out.println("Got movie: " + movie);
        
        library.saveMovie(movie);
      }

    } catch (ServerCommunicationException e) {
      e.printStackTrace();
    } catch (TmdbError e) {
      e.printStackTrace();
    }
  }

}