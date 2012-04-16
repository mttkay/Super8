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
  
  public List<Movie> getMovieSuggestions() {
    return movieDao.getAll(MovieTable.Columns.STATE + "=" + Movie.STATE_DEFAULT);
  }
  
  public int getSuggestionsCount() {
    return movieDao.count(MovieTable.Columns.STATE + "=" + Movie.STATE_DEFAULT);
  }

  public void savePerson(Person person) {
    personDao.save(person);
  }

  public void deletePerson(Person person) {
    personDao.delete(person);
  }
}
