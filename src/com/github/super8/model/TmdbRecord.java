package com.github.super8.model;

public class TmdbRecord {

  private int tmdbId;

  public int getTmdbId() {
    return tmdbId;
  }

  public void setTmdbId(int id) {
    this.tmdbId = id;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + ":" + tmdbId;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj.getClass().equals(getClass())) {
      return ((TmdbRecord) obj).tmdbId == this.tmdbId;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return new Integer(tmdbId).hashCode();
  }
}
