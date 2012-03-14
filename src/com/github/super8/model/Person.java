package com.github.super8.model;

import java.util.Date;

import android.content.Context;

import com.github.super8.apis.tmdb.v3.TmdbApi;

public class Person {

  private int tmdbId;
  private String name, biography, imagePath, scaledImageUrl;
  private Date birthday;

  public int getTmdbId() {
    return tmdbId;
  }
  
  public void setTmdbId(int id) {
    this.tmdbId = id;
  }
  
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
}
