package com.github.super8.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.super8.model.Movie;
import com.github.super8.model.Person;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LibraryManager {

  public static final String DATABASE_NAME = "library.db";
  public static final int DATABASE_VERSION = 1;

  private SQLiteDatabase db;

  private MovieDao movieDao;
  private PersonDao personDao;

  @Inject
  public LibraryManager(Context context) {
    this.db = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
        .getWritableDatabase();
    this.movieDao = new MovieDao(db);
    this.personDao = new PersonDao(db);
  }

  public void saveMovie(Movie movie) {
    movieDao.save(movie);
  }

  public Movie getMovie(int tmdbId) {
    return movieDao.get(tmdbId);
  }

  public List<Movie> getWatchlist() {
    return movieDao.getAll(MovieTable.Columns.STATE + "=" + Movie.STATE_MUST_SEE);
  }

  public int getWatchlistSize() {
    return movieDao.count(MovieTable.Columns.STATE + "=" + Movie.STATE_MUST_SEE);
  }
  
  public boolean hasWatchlistItems() {
    return getWatchlistSize() > 0;
  }
  
  public void addToWatchlist(Movie movie) {
    movie.setState(Movie.STATE_MUST_SEE);
    movieDao.update(movie);
  }
  
  public void removeFromWatchlist(Movie movie) {
    movie.setState(Movie.STATE_DEFAULT);
    movieDao.update(movie);
  }
  
  public void markAsSeen(Movie movie) {
    movie.setState(Movie.STATE_SEEN_IT);
    movieDao.update(movie);
  }
  
  public void ignoreMovie(Movie movie) {
    movie.setState(Movie.STATE_IGNORE);
    movieDao.update(movie);
  }
  
  public List<Movie> getMovieSuggestions() {
    return movieDao.getAll(MovieTable.Columns.STATE + "=" + Movie.STATE_DEFAULT);
  }
  
  public int getSuggestionsCount() {
    return movieDao.count(MovieTable.Columns.STATE + "=" + Movie.STATE_DEFAULT);
  }
  
  public boolean hasSuggestions() {
    return getSuggestionsCount() > 0;
  }

  public void savePerson(Person person) {
    personDao.save(person);
  }

  public void deletePerson(Person person) {
    personDao.delete(person);
  }
  
  public boolean hasPerson(Person person) {
    return personDao.count(PersonTable.Columns._ID + "=" + person.getTmdbId()) > 0;
  }
}
