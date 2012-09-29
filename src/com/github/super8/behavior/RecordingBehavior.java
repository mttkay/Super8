package com.github.super8.behavior;

import com.github.super8.model.Person;

public interface RecordingBehavior {

  void show(boolean show);

  void onPersonSelected(Person person);

  boolean onBackPressed();
}
