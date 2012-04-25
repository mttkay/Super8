package com.github.super8.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  public int update(T model) {
    return 0;
  }

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

  public List<T> getAll(String where, String... whereArgs) {
    List<T> records = null;
    Cursor c = db.query(tableName, null, where, whereArgs, null, null, null);
    int count = c.getCount();
    if (count > 0) {
      records = new ArrayList<T>(count);
      while (c.moveToNext()) {
        records.add(buildModel(c));
      }
    } else {
      records = Collections.emptyList();
    }
    c.close();

    return records;
  }

  public int count(String where) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT COUNT(*) FROM " + tableName);
    if (where != null) {
      query.append(" WHERE " + where);
    }
    Cursor c = db.rawQuery(query.toString(), null);
    c.moveToNext();
    int count = c.getInt(0);
    c.close();
    return count;
  }

  protected void bindOptString(int index, String value) {
    if (value == null) {
      insert.bindNull(index);
    } else {
      insert.bindString(index, value);
    }
  }
}
