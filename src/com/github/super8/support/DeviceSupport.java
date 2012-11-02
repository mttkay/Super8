package com.github.super8.support;

import android.content.Context;

import com.github.super8.R;

public class DeviceSupport {

  public static boolean isLargeDevice(Context context) {
    return context.getResources().getBoolean(R.bool.large_device);
  }
  
}
