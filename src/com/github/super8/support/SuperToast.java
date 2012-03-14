package com.github.super8.support;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ignition.support.IgnitedScreens;
import com.github.super8.R;

public class SuperToast {

  private static final int MARGIN_TOP = 5;

  private static final class ViewHolder {
    private TextView textView;
  }

  private static ViewHolder viewHolder;

  private static Toast build(Context context, int iconId, String message) {
    Context appContext = context.getApplicationContext();

    if (viewHolder == null) {
      viewHolder = new ViewHolder();
      LayoutInflater inflater = (LayoutInflater) appContext
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      viewHolder.textView = (TextView) inflater.inflate(R.layout.toast, null);
    }

    viewHolder.textView.setText(message);
    viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0);

    Toast toast = new Toast(appContext);
    toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0,
        IgnitedScreens.dipToPx(appContext, MARGIN_TOP));
    toast.setDuration(Toast.LENGTH_LONG);
    toast.setView(viewHolder.textView);
    return toast;
  }

  private static Toast build(Context context, int iconId, int messageId) {
    return build(context, iconId, context.getString(messageId));
  }

  public static void info(Context context, int messageId) {
    Toast toast = build(context, android.R.drawable.ic_dialog_info, messageId);
    viewHolder.textView.setBackgroundDrawable(context.getResources().getDrawable(
        R.drawable.toast_info_background));
    toast.show();
  }

  public static void error(Context context, int messageId) {
    Toast toast = build(context, android.R.drawable.ic_dialog_alert, messageId);
    viewHolder.textView.setBackgroundDrawable(context.getResources().getDrawable(
        R.drawable.toast_error_background));
    toast.show();
  }

  public static void error(Context context, Exception error) {
    Toast toast = build(context, android.R.drawable.ic_dialog_alert, error.getLocalizedMessage());
    viewHolder.textView.setBackgroundDrawable(context.getResources().getDrawable(
        R.drawable.toast_error_background));
    toast.show();
  }

}
