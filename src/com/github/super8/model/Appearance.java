package com.github.super8.model;

import java.util.Date;

public class Appearance extends TmdbRecord {

  private String name, moviePosterPath, movieTitle, personProfilePath;
  private Date movieReleaseDate;

  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
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
  
  public String getPersonProfilePath() {
    return personProfilePath;
  }
  
  public void setPersonProfilePath(String personProfilePath) {
    this.personProfilePath = personProfilePath;
  }
  
  @Override
  public String toString() {
    return name;
  }
}
