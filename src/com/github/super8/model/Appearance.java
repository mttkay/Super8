package com.github.super8.model;

import java.util.Date;

public class Appearance extends TmdbRecord {

  private String moviePosterPath, movieTitle;
  private Date movieReleaseDate;

  public String getPosterPath() {
    return moviePosterPath;
  }

  public void setMoviePosterPath(String posterPath) {
    this.moviePosterPath = posterPath;
  }

  public Date getMovieReleaseDate() {
    return movieReleaseDate;
  }

  public void setMovieReleaseDate(Date releaseDate) {
    this.movieReleaseDate = releaseDate;
  }

  public String getMovieTitle() {
    return movieTitle;
  }

  public void setMovieTitle(String movieTitle) {
    this.movieTitle = movieTitle;
  }
  
}
