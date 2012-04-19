package com.github.super8.apis.tmdb.v3;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.github.ignition.support.http.IgnitedHttp;
import com.github.super8.apis.ServerCommunicationException;
import com.github.super8.apis.tmdb.v3.parsers.TmdbCreditsParser;
import com.github.super8.apis.tmdb.v3.parsers.TmdbMovieParser;
import com.github.super8.apis.tmdb.v3.parsers.TmdbPersonParser;
import com.github.super8.model.Credits;
import com.github.super8.model.Movie;
import com.github.super8.model.Person;
import com.google.inject.Inject;

public class TmdbApi {

  public static final int DEFAULT_PAGE_SIZE = 20;
  public static final String IMAGE_BASE_URL = "http://cf2.imgobject.com/t/p/";

  @SuppressWarnings("unused") private static final String LOG_TAG = TmdbApi.class.getName();

  private static final String API_KEY = "bace5d090265e765e78bf188f414783e";
  private static final String ENDPOINT = "http://api.themoviedb.org/3";
  private static final String FORMAT = "application/json";

  private final IgnitedHttp http;

  @Inject
  public TmdbApi(IgnitedHttp http) {
    this.http = http;
    http.setDefaultHeader("Accept", FORMAT);
  }

  public TmdbApiTask<Movie> backfillMovie(TmdbApiHandler<Movie> handler, Movie targetMovie) {
    TmdbFetchOneTask<Movie> task = new TmdbFetchOneTask<Movie>(http, new TmdbMovieParser(),
        targetMovie);
    task.connect(handler);
    task.execute(ENDPOINT + "/movie/" + targetMovie.getTmdbId() + "?api_key=" + API_KEY);
    return task;
  }

  public Movie getMovie(int id) throws ServerCommunicationException, TmdbError {
    TmdbFetchOneTask<Movie> task = new TmdbFetchOneTask<Movie>(http, new TmdbMovieParser());
    return task.run(ENDPOINT + "/movie/" + id + "?api_key=" + API_KEY);
  }

  public TmdbApiTask<List<Movie>> searchMovie(TmdbApiHandler<List<Movie>> handler, String query) {
    TmdbFetchManyTask<Movie> task = new TmdbFetchManyTask<Movie>(http, new TmdbMovieParser());
    task.connect(handler);
    task.execute(searchUrl("movie", query));
    return task;
  }

  public TmdbApiTask<Person> getPerson(TmdbApiHandler<Person> handler, int personId) {
    TmdbFetchOneTask<Person> task = new TmdbFetchOneTask<Person>(http, new TmdbPersonParser());
    task.connect(handler);
    task.execute(ENDPOINT + "/person/" + personId + "?api_key=" + API_KEY);
    return task;
  }

  public TmdbApiTask<List<Person>> searchPerson(TmdbApiHandler<List<Person>> handler, String query) {
    TmdbFetchManyTask<Person> task = new TmdbFetchManyTask<Person>(http, new TmdbPersonParser());
    task.connect(handler);
    task.execute(searchUrl("person", query));
    return task;
  }

  public TmdbApiTask<Credits> getCredits(TmdbApiHandler<Credits> handler, int personId) {
    TmdbFetchOneTask<Credits> task = new TmdbFetchOneTask<Credits>(http, new TmdbCreditsParser());
    task.connect(handler);
    task.execute(ENDPOINT + "/person/" + personId + "/credits?api_key=" + API_KEY);
    return task;
  }

  public Credits getCredits(int personId) throws TmdbError, ServerCommunicationException {
    TmdbFetchOneTask<Credits> task = new TmdbFetchOneTask<Credits>(http, new TmdbCreditsParser());
    return task.run(ENDPOINT + "/person/" + personId + "/credits?api_key=" + API_KEY);
  }

  private String searchUrl(String resource, String query) {
    try {
      return ENDPOINT + "/search/" + resource + "?api_key=" + API_KEY + "&query="
          + URLEncoder.encode(query, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }
}
