package com.github.super8.db;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class MovieTable {

  public static final String NAME = "movies";

  public interface Columns extends BaseColumns {
    final String TITLE = "title";
    final String IMAGE = "image_path";
    final String STATE = "state";
  }

  public static void onCreate(SQLiteDatabase db) {
    StringBuilder sb = new StringBuilder();
    sb.append(DatabaseHelper.startTable(NAME));
    sb.append(DatabaseHelper.idAsPrimaryKey());
    sb.append(",");
    sb.append(Columns.TITLE + " TEXT NOT NULL");
    sb.append(",");
    sb.append(Columns.IMAGE + " TEXT");
    sb.append(",");
    sb.append(Columns.STATE + " INTEGER NOT NULL DEFAULT 0");
    sb.append(DatabaseHelper.endTable());
    db.execSQL(sb.toString());
  }

  public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(DatabaseHelper.dropTable(NAME));
    onCreate(db);
  }

}
