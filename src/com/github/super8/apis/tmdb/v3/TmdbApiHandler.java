package com.github.super8.apis.tmdb.v3;

import android.content.Context;

import com.github.ignition.core.tasks.IgnitedAsyncTaskHandler;

public interface TmdbApiHandler<ReturnT> extends
    IgnitedAsyncTaskHandler<Context, Void, ReturnT> {
}
