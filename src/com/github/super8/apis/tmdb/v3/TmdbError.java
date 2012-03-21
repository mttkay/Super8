package com.github.super8.apis.tmdb.v3;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.ignition.support.http.IgnitedHttpResponse;

@SuppressWarnings("serial")
public class TmdbError extends Exception {

  private int statusCode;
  private int errorCode;
  private String errorMessage;

  public TmdbError(IgnitedHttpResponse response) throws JSONException, IOException {
    JSONObject errorObject = new JSONObject(response.getResponseBodyAsString());
    statusCode = response.getStatusCode();
    errorCode = errorObject.getInt("status_code");
    errorMessage = errorObject.getString("status_message");
  }

  @Override
  public String getMessage() {
    return "TMDB answered with " + statusCode + "\n\nError code " + errorCode + ": " + errorMessage;
  }
}
