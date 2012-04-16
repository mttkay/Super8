package com.github.super8.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.super8.model.Person;

public class PersonDao extends AbstractDao<Person> {

  private static final String INSERT = "INSERT INTO " + PersonTable.NAME + " ("
      + PersonTable.Columns._ID + "," + PersonTable.Columns.NAME + ") VALUES (?, ?)";

  public PersonDao(SQLiteDatabase db) {
    super(db, PersonTable.NAME, INSERT);
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
  protected Person buildModel(Cursor c) {
    Person person = new Person();
    person.setTmdbId(c.getInt(0));
    person.setName(c.getString(1));
    return person;
  }

}
