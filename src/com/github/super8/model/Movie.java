package com.github.super8.model;

import java.util.Date;

public class Movie extends ImageRecord {

  public static final int STATE_DEFAULT = 0;
  public static final int STATE_SEEN_IT = 1;
  public static final int STATE_MUST_SEE = 2;
  public static final int STATE_IGNORE = 3;
  
  // TMDB attributes
  private String imdbId, title;
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
}
