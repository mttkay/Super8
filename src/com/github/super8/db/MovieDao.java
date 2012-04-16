package com.github.super8.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.github.super8.model.Movie;

public class MovieDao implements Dao<Movie> {

  private static final String INSERT = "INSERT INTO " + MovieTable.NAME + " ("
      + MovieTable.Columns._ID + "," + MovieTable.Columns.STATE + ") VALUES (?,?)";

  private SQLiteDatabase db;
  private SQLiteStatement insert;

  public MovieDao(SQLiteDatabase db) {
    this.db = db;
    this.insert = db.compileStatement(INSERT);
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
  public boolean delete(Movie model) {
    int rowsDeleted = db.delete(MovieTable.NAME, MovieTable.Columns._ID + " = ?",
        new String[] { String.valueOf(model.getTmdbId()) });
    return rowsDeleted > 0;
  }

  @Override
  public Movie get(int id) {
    Cursor c = db.query(MovieTable.NAME, null, MovieTable.Columns._ID + "=" + id, null, null,
        null, null);
    
    Movie movie = null;
    if (c.moveToNext()) {
      movie = new Movie();
      movie.setTmdbId(c.getInt(0));
      movie.setState(c.getInt(1));
    }
    return movie;
  }

}
