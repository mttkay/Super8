package com.github.super8.model;

import android.content.Context;
import android.os.Parcel;

import com.github.super8.apis.tmdb.v3.TmdbApi;

public abstract class ImageRecord extends TmdbRecord {

  private String imagePath, backdropPath, scaledImageUrl, scaledBackdropUrl;

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
  
  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }
  
  public String getScaledBackdropUrl(Context context) {
    if (scaledBackdropUrl == null && backdropPath != null) {
      String sizeSpec = null;
      // if (IgnitedScreens.getScreenDensity(context) > IgnitedScreens.SCREEN_DENSITY_MEDIUM) {
      sizeSpec = "w780";
      // } else {
      // sizeSpec = "w45";
      // }
      scaledBackdropUrl = TmdbApi.IMAGE_BASE_URL + sizeSpec + backdropPath;
    }

    return scaledBackdropUrl;
  }  

  @Override
  protected void readFromParcel(Parcel source) {
    super.readFromParcel(source);
    this.imagePath = source.readString();
    this.scaledImageUrl = source.readString();
    this.backdropPath = source.readString();
    this.scaledBackdropUrl = source.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(imagePath);
    dest.writeString(scaledImageUrl);
    dest.writeString(backdropPath);
    dest.writeString(scaledBackdropUrl);
  }
}
