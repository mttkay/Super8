package com.github.super8.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.super8.model.Movie;

public class MovieDao extends AbstractDao<Movie> {

  private static final String TAG = MovieDao.class.getSimpleName();

  private static final String INSERT = "INSERT OR REPLACE INTO " + MovieTable.NAME + " ("
      + MovieTable.Columns._ID + "," + MovieTable.Columns.TITLE + "," + MovieTable.Columns.IMAGE
      + "," + MovieTable.Columns.STATE + ") VALUES (?,?,?,?)";

  public MovieDao(SQLiteDatabase db) {
    super(db, MovieTable.NAME, INSERT);
  }

  @Override
  public int save(Movie model) {
    Log.d(TAG, "Saving movie");
    insert.clearBindings();
    insert.bindLong(1, model.getTmdbId());
    insert.bindString(2, model.getTitle());
    bindOptString(3, model.getImagePath());
    insert.bindLong(4, model.getState());
    insert.executeInsert();
    Log.d(TAG, "Movie saved! (ID = " + model.getTmdbId() + ")");
    return model.getTmdbId();
  }

  @Override
  public int update(Movie movie) {
    Log.d(TAG, "Updating movie (ID = " + movie.getTmdbId() + ")");
    ContentValues values = new ContentValues();
    values.put(MovieTable.Columns.TITLE, movie.getTitle());
    values.put(MovieTable.Columns.IMAGE, movie.getImagePath());
    values.put(MovieTable.Columns.STATE, movie.getState());
    int rowsUpdated = db.update(MovieTable.NAME, values,
        MovieTable.Columns._ID + "=" + movie.getTmdbId(), null);
    Log.d(TAG, rowsUpdated + " rows affected");
    return rowsUpdated;
  }

  @Override
  protected Movie buildModel(Cursor c) {
    Movie movie = new Movie();
    movie.setTmdbId(c.getInt(0));
    movie.setTitle(c.getString(1));
    movie.setImagePath(c.getString(2));
    movie.setState(c.getInt(3));
    return movie;
  }

}
