package com.github.super8.apis.tmdb.v3;

import com.github.ignition.support.http.IgnitedHttp;
import com.github.ignition.support.http.IgnitedHttpResponse;
import com.github.super8.apis.ServerCommunicationException;
import com.github.super8.apis.tmdb.v3.parsers.TmdbParser;
import com.github.super8.model.TmdbRecord;

public class TmdbFetchOneTask<ModelT extends TmdbRecord> extends TmdbApiTask<ModelT> {

  private TmdbParser<ModelT> parser;

  public TmdbFetchOneTask(IgnitedHttp http, TmdbParser<ModelT> parser) {
    super(http);
    this.parser = parser;
  }

  @Override
  protected ModelT handleResponse(IgnitedHttpResponse response) throws ServerCommunicationException {
    try {
      return parser.parseOne(response.getResponseBodyAsString());
    } catch (Exception e) {
      throw new ServerCommunicationException(e);
    }
  }
}
