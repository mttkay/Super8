package com.github.super8.apis.tmdb.v3;

import org.json.JSONObject;

import com.github.ignition.support.http.IgnitedHttpResponse;
import com.github.super8.apis.ServerCommunicationException;

@SuppressWarnings("serial")
public class TmdbError extends Exception {

  private int statusCode;
  private int errorCode;
  private String errorMessage;

  public TmdbError(IgnitedHttpResponse response) throws ServerCommunicationException {
    try {
      JSONObject errorObject = new JSONObject(response.getResponseBodyAsString());
      statusCode = response.getStatusCode();
      errorCode = errorObject.getInt("status_code");
      errorMessage = errorObject.getString("status_message");
    } catch (Exception e) {
      throw new ServerCommunicationException(e);
    }
  }

  @Override
  public String getMessage() {
    return "TMDB answered with " + statusCode + "\n\nError code " + errorCode + ": " + errorMessage;
  }
}
