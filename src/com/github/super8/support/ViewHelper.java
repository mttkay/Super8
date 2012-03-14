package com.github.super8.support;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.github.super8.R;

public class ViewHelper {

    public static void showProgressSpinner(Activity context, boolean show) {
        ProgressBar spinner = (ProgressBar) context.findViewById(R.id.progress_spinner);
        spinner.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    
}
