package com.github.super8.views;

import java.util.Random;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Handler;
import android.os.Message;

public class FlickerDrawable extends LevelListDrawable implements android.os.Handler.Callback {

  private static final int LEVEL_OFF = 0;
  private static final int LEVEL_ON = 1;

  private Handler handler = new Handler(this);
  private Random random = new Random();

  public FlickerDrawable(Drawable offDrawable, Drawable onDrawable) {
    addLevel(LEVEL_OFF, LEVEL_OFF, offDrawable);
    addLevel(LEVEL_ON, LEVEL_ON, onDrawable);
    handler.sendEmptyMessageDelayed(0, nextTime());
  }

  private long nextTime() {
    return random.nextInt(1000);
  }

  @Override
  public boolean handleMessage(Message msg) {
    if (getLevel() == LEVEL_OFF) {
      setLevel(LEVEL_ON);
    } else {
      setLevel(LEVEL_OFF);
    }
    handler.sendEmptyMessageDelayed(0, nextTime());
    return true;
  }
}
