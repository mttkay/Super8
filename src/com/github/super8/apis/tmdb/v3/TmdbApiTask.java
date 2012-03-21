package com.github.super8.apis.tmdb.v3;

import java.io.IOException;

import android.content.Context;
import android.util.Log;

import com.github.ignition.core.tasks.IgnitedAsyncTask;
import com.github.ignition.support.http.IgnitedHttp;
import com.github.ignition.support.http.IgnitedHttpResponse;
import com.github.super8.apis.ServerCommunicationException;
import com.github.super8.support.SuperToast;

public abstract class TmdbApiTask<ModelT> extends IgnitedAsyncTask<Context, String, Void, ModelT> {

  private static final String LOG_TAG = TmdbApiTask.class.getName();

  private IgnitedHttp http;

  public TmdbApiTask(IgnitedHttp http) {
    this.http = http;
  }

  @Override
  protected ModelT run(String url) throws Exception {
    try {
      Log.d(LOG_TAG, "url: " + url);
      IgnitedHttpResponse response = http.get(url).send();

      int statusCode = response.getStatusCode();
      Log.d(LOG_TAG, "status: " + statusCode);
      if (statusCode != 200) {
        throw new TmdbError(response);
      }
      return handleResponse(response);
    } catch (IOException e) {
      throw new ServerCommunicationException(e);
    }
  }

  @Override
  public boolean onTaskFailed(Context context, Exception error) {
    super.onTaskFailed(context, error);
    SuperToast.error(context, error);
    return true;
  }

  protected abstract ModelT handleResponse(IgnitedHttpResponse response) throws Exception;

}
