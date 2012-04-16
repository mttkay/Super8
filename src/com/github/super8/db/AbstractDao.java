package com.github.super8.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.github.super8.model.TmdbRecord;

public abstract class AbstractDao<T extends TmdbRecord> {

  protected SQLiteDatabase db;
  protected SQLiteStatement insert;

  private String tableName;

  public AbstractDao(SQLiteDatabase db, String tableName, String insertStatement) {
    this.db = db;
    this.tableName = tableName;
    this.insert = db.compileStatement(insertStatement);
  }

  public abstract int save(T model);

  public boolean delete(T model) {
    int rowsDeleted = db.delete(tableName, BaseColumns._ID + " = ?",
        new String[] { String.valueOf(model.getTmdbId()) });
    return rowsDeleted > 0;
  }

  public T get(int id) {
    T record = null;
    Cursor c = db.query(tableName, null, BaseColumns._ID + "=" + id, null, null, null, null);
    if (c.moveToNext()) {
      record = buildModel(c);
    }
    c.close();
    return record;
  }

  protected abstract T buildModel(Cursor c);
}
