package com.github.super8.apis.imdb;

import android.content.Context;

import com.google.inject.Provider;

public abstract class ImdbApiTask<ResultT> {

    protected final ImdbApi api;

    protected Provider<Context> contextProvider;

    public ImdbApiTask() {
        this.api = new ImdbApi();
    }

    // @Inject
    public void setContextProvider(Provider<Context> contextProvider) {
        this.contextProvider = contextProvider;
    }
}
