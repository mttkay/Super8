package com.github.super8.apis.tmdb.v3;

import android.content.Context;

import com.github.ignition.core.tasks.IgnitedAsyncTaskDelegateHandler;

public interface TmdbApiHandler<ReturnT> extends
    IgnitedAsyncTaskDelegateHandler<Context, Void, ReturnT> {
}
