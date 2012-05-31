package com.github.super8.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.super8.support.IgnitedParcels;

public class Movie extends ImageRecord {

  public static final int STATE_DEFAULT = 0;
  public static final int STATE_SEEN_IT = 1;
  public static final int STATE_MUST_SEE = 2;
  public static final int STATE_IGNORE = 3;

  // TMDB attributes
  private String imdbId, title, synopsis;
  private Date releaseDate;

  // other attributes
  private int state = STATE_DEFAULT;

  public String getImdbId() {
    return imdbId;
  }

  public void setImdbId(String imdbId) {
    this.imdbId = imdbId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  @Override
  protected void readFromParcel(Parcel source) {
    super.readFromParcel(source);
    imdbId = source.readString();
    title = source.readString();
    synopsis = source.readString();
    state = source.readInt();
    releaseDate = IgnitedParcels.readDate(source);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(imdbId);
    dest.writeString(title);
    dest.writeString(synopsis);
    dest.writeInt(state);
    IgnitedParcels.writeDate(dest, releaseDate);
  }

  public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

    @Override
    public Movie createFromParcel(Parcel source) {
      Movie movie = new Movie();
      movie.readFromParcel(source);
      return movie;
    }

    @Override
    public Movie[] newArray(int size) {
      return new Movie[size];
    }
  };
}
