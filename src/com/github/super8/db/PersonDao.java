package com.github.super8.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.github.super8.model.Person;

public class PersonDao implements Dao<Person> {

  private static final String INSERT = "INSERT INTO " + PersonTable.NAME + " ("
      + PersonTable.Columns._ID + "," + PersonTable.Columns.NAME + ") VALUES (?, ?)";

  private SQLiteDatabase db;
  private SQLiteStatement insert;

  public PersonDao(SQLiteDatabase db) {
    this.db = db;
    this.insert = db.compileStatement(INSERT);
  }

  @Override
  public int save(Person model) {
    insert.clearBindings();
    insert.bindLong(1, model.getTmdbId());
    insert.bindString(2, model.getName());
    insert.executeInsert();
    return model.getTmdbId();
  }

  @Override
  public boolean delete(Person model) {
    int rowsDeleted = db.delete(PersonTable.NAME, PersonTable.Columns._ID + " = ?",
        new String[] { String.valueOf(model.getTmdbId()) });
    return rowsDeleted > 0;
  }

  @Override
  public Person get(int id) {
    Cursor c = db.query(PersonTable.NAME, null, PersonTable.Columns._ID + "=" + id, null, null,
        null, null);

    Person person = null;
    if (c.moveToNext()) {
      person = new Person();
      person.setTmdbId(c.getInt(0));
      person.setName(c.getString(1));
    }
    return person;
  }

}
