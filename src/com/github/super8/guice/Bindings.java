package com.github.super8.guice;

import com.github.ignition.support.http.IgnitedHttp;
import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class Bindings extends AbstractModule {

  // private Super8Application application;

  @Override
  protected void configure() {
    bind(IgnitedHttp.class).in(Singleton.class);
    bind(TmdbApi.class);
    // bindConstant().annotatedWith(SharedPreferencesName.class).to(
    // "com.github.super8.Super8_preferences");
  }

  // @Provides
  // IgnitedHttp provideIgnitionHttpInstance() {
  // return application.getIgnitedHttp();
  // }
}
