package com.github.super8.db;

public interface Dao<T> {
  int save(T model);
  boolean delete(T model);
  T get(int id);
}
