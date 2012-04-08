package com.github.super8.db;

import android.database.sqlite.SQLiteDatabase;

public class MovieGenresTable {

  public static final String NAME = "movie_genres";

  public interface Columns {
    final String MOVIE_ID = "movie_id";
    final String GENRE_ID = "genre_id";
  }

  public static void onCreate(SQLiteDatabase db) {
    StringBuilder sb = new StringBuilder();
    sb.append(DatabaseHelper.startTable(NAME));
    sb.append(Columns.MOVIE_ID + " INTEGER NOT NULL,");
    sb.append(Columns.GENRE_ID + " INTEGER NOT NULL,");
    sb.append(DatabaseHelper.foreignKey(Columns.MOVIE_ID, MovieTable.NAME, MovieTable.Columns._ID));
    sb.append(",");
    sb.append(DatabaseHelper.foreignKey(Columns.GENRE_ID, GenreTable.NAME, GenreTable.Columns._ID));
    sb.append(") PRIMARY KEY (");
    sb.append(Columns.MOVIE_ID + "," + Columns.GENRE_ID);
    sb.append(DatabaseHelper.endTable());
    db.execSQL(sb.toString());
  }

  public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(DatabaseHelper.dropTable(NAME));
    onCreate(db);
  }

}
