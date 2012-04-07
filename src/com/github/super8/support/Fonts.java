package com.github.super8.support;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Typeface;

public class Fonts {

  private static final HashMap<String, Typeface> fonts = new HashMap<String, Typeface>();

  public static Typeface loadFont(Context context, String filename) {
    Typeface font = fonts.get(filename);
    if (font == null) {
      font = Typeface.createFromAsset(context.getAssets(), "fonts/" + filename);
      fonts.put(filename, font);
    }
    return font;
  }
  
  public static Typeface robotoThin(Context context) {
    return loadFont(context, "Roboto-Thin.ttf");
  }

}
