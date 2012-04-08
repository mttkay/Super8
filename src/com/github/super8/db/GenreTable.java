package com.github.super8.db;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class GenreTable {

  public static final String NAME = "genres";

  public interface Columns extends BaseColumns {
    final String NAME = "name";
  }

  public static void onCreate(SQLiteDatabase db) {
    StringBuilder sb = new StringBuilder();
    sb.append(DatabaseHelper.startTable(NAME));
    sb.append(DatabaseHelper.idAsPrimaryKey());
    sb.append(",");
    sb.append(Columns.NAME + " TEXT NOT NULL");
    sb.append(DatabaseHelper.endTable());
    db.execSQL(sb.toString());
  }

  public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(DatabaseHelper.dropTable(NAME));
    onCreate(db);
  }

}
