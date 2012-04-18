package com.github.super8.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.super8.model.Movie;

public class MovieDao extends AbstractDao<Movie> {

  private static final String INSERT = "INSERT OR REPLACE INTO " + MovieTable.NAME + " ("
      + MovieTable.Columns._ID + "," + MovieTable.Columns.STATE + ") VALUES (?,?)";

  public MovieDao(SQLiteDatabase db) {
    super(db, MovieTable.NAME, INSERT);
  }

  @Override
  public int save(Movie model) {
    insert.clearBindings();
    insert.bindLong(1, model.getTmdbId());
    insert.bindLong(2, model.getState());
    insert.executeInsert();
    return model.getTmdbId();
  }

  @Override
  protected Movie buildModel(Cursor c) {
    Movie movie = new Movie();
    movie.setTmdbId(c.getInt(0));
    movie.setState(c.getInt(1));
    return movie;
  }

}
