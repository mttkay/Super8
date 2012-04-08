package com.github.super8.model;

import java.util.Date;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.github.super8.apis.tmdb.v3.TmdbApi;

public class Person extends TmdbRecord {

  private String name, biography, imagePath, scaledImageUrl;
  private Date birthday;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getImagePath() {
    return imagePath;
  }

  public String getScaledImageUrl(Context context) {
    if (scaledImageUrl == null && imagePath != null) {
      String sizeSpec = null;
      // if (IgnitedScreens.getScreenDensity(context) > IgnitedScreens.SCREEN_DENSITY_MEDIUM) {
      sizeSpec = "w185";
      // } else {
      // sizeSpec = "w45";
      // }
      scaledImageUrl = TmdbApi.IMAGE_BASE_URL + sizeSpec + imagePath;
    }

    return scaledImageUrl;
  }

  @Override
  public String toString() {
    return name;
  }

  public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
    @Override
    public Person createFromParcel(Parcel source) {
      Person person = new Person();
      person.readFromParcel(source);
      return person;
    }

    @Override
    public Person[] newArray(int size) {
      return new Person[size];
    }
  };

  @Override
  protected void readFromParcel(Parcel source) {
    super.readFromParcel(source);
    this.name = source.readString();
    this.biography = source.readString();
    long epoch = source.readLong();
    if (epoch > -1) {
      this.birthday = new Date(epoch);
    }
    this.imagePath = source.readString();
    this.scaledImageUrl = source.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(name);
    dest.writeString(biography);
    if (birthday != null) {
      dest.writeLong(birthday.getTime());
    } else {
      dest.writeLong(-1);
    }
    dest.writeString(imagePath);
    dest.writeString(scaledImageUrl);
  }

}
