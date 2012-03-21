package com.github.super8.apis.tmdb.v3;

import android.content.Context;

import com.github.ignition.core.tasks.IgnitedAsyncTaskDefaultHandler;

public class DefaultTmdbApiHandler<ReturnT> extends
    IgnitedAsyncTaskDefaultHandler<Context, Void, ReturnT> implements TmdbApiHandler<ReturnT> {

  public DefaultTmdbApiHandler(Context context) {
    super(context);
  }

}
