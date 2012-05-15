package com.github.super8.support;

import java.util.Date;

import android.os.Parcel;

public class IgnitedParcels {

  public static void writeDate(Parcel dest, Date date) {
    if (date != null) {
      dest.writeLong(date.getTime());
    } else {
      dest.writeLong(-1L);
    }
  }

  public static Date readDate(Parcel source) {
    long date = source.readLong();
    if (date > -1) {
      return new Date(date);
    }

    return null;
  }

}
