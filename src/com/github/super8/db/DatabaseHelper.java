package com.github.super8.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

  public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    MovieTable.onCreate(db);
    PersonTable.onCreate(db);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    MovieTable.onUpgrade(db, oldVersion, newVersion);
    PersonTable.onUpgrade(db, oldVersion, newVersion);
  }

  public static String foreignKey(String sourceColumn, String targetTable, String targetColumn) {
    return "FOREIGN KEY (" + sourceColumn + ") REFERENCES " + targetTable + "(" + targetColumn
        + ")";
  }

  public static String startTable(String tableName) {
    return "CREATE TABLE " + tableName + "(";
  }

  public static String endTable() {
    return ");";
  }

  public static String dropTable(String tableName) {
    return "DROP TABLE IF EXISTS " + tableName;
  }

  public static String idAsPrimaryKey() {
    return BaseColumns._ID + " INTEGER PRIMARY KEY";
  }
}
