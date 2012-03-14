package com.github.super8;

import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Preferences {

  private static final String PREF_FIRST_LAUNCH = "pref_first_launch";

  @Inject
  private SharedPreferences preferences;

  public boolean isFirstLaunch() {
    boolean firstLaunch = preferences.getBoolean(PREF_FIRST_LAUNCH, true);
    if (firstLaunch) {
      preferences.edit().putBoolean(PREF_FIRST_LAUNCH, false).commit();
    }
    return firstLaunch;
  }
}
