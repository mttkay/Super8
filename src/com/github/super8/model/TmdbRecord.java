package com.github.super8.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TmdbRecord implements Parcelable {

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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(tmdbId);
  }

  protected void readFromParcel(Parcel source) {
    this.tmdbId = source.readInt();
  }
}
