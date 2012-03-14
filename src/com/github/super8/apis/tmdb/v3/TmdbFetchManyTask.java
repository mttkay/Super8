package com.github.super8.apis.tmdb.v3;

import java.util.List;

import org.json.JSONException;

import com.github.ignition.support.http.IgnitedHttp;
import com.github.ignition.support.http.IgnitedHttpResponse;
import com.github.super8.apis.ServerCommunicationException;
import com.github.super8.apis.tmdb.v3.parsers.TmdbParser;

public class TmdbFetchManyTask<ModelT> extends TmdbApiTask<List<ModelT>> {

  private TmdbParser<ModelT> parser;

  public TmdbFetchManyTask(IgnitedHttp http, TmdbParser<ModelT> parser) {
    super(http);
    this.parser = parser;
  }

  @Override
  protected List<ModelT> handleResponse(IgnitedHttpResponse response) throws Exception {
    try {
      return parser.parseList(response.getResponseBodyAsString());
    } catch (JSONException e) {
      throw new ServerCommunicationException(e);
    }
  }
}
