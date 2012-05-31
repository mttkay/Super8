package com.github.super8.apis.tmdb.v3.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.super8.model.Movie;

public class TmdbMovieParser extends TmdbParser<Movie> {

  @Override
  public Movie parseModel(JSONObject modelObject, Movie movie) throws JSONException {
    if (movie == null) {
      movie = new Movie();
    }
    movie.setTitle(modelObject.getString("title"));
    movie.setReleaseDate(parseDate(modelObject.getString("release_date")));

    movie.setBackdropPath(parseString(modelObject.getString("backdrop_path")));
    movie.setImagePath(parseString(modelObject.getString("poster_path")));

    movie.setSynopsis(parseString(modelObject.optString("overview")));

    return movie;
  }

}
