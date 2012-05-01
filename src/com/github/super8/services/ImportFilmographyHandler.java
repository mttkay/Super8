package com.github.super8.services;

import android.os.Handler;
import android.os.Message;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.github.super8.R;
import com.github.super8.fragments.PersonDetailsFragment;
import com.github.super8.model.Movie;
import com.github.super8.support.SuperToast;

public class ImportFilmographyHandler implements Handler.Callback {
  private int movieCount;
  private int importedCount;

  private SherlockFragmentActivity activity;
  private ActionBar actionBar;

  public ImportFilmographyHandler(SherlockFragmentActivity activity) {
    this.activity = activity;
    this.actionBar = activity.getSupportActionBar();
  }

  public void detach() {
    this.actionBar = null;
    this.activity = null;
  }

  @Override
  public boolean handleMessage(Message msg) {
    if (activity == null) {
      return true;
    }

    switch (msg.what) {
    case PersonDetailsFragment.MSG_IMPORT_STARTED:
      handleImportStartedMessage(msg);
      break;
    case PersonDetailsFragment.MSG_MOVIE_IMPORTED:
      handleMovieImportedMessage(msg);
      break;
    case PersonDetailsFragment.MSG_IMPORT_SUCCEEDED:
      handleImportFinishedMessage();
      break;
    case PersonDetailsFragment.MSG_IMPORT_FAILED:
      handleImportFailedMessage(msg);
      break;
    }

    return true;
  }

  private void handleImportFailedMessage(Message msg) {
    SuperToast.error(activity, msg.getData().getString("error"));
  }

  private void handleImportFinishedMessage() {
    SuperToast.info(activity, activity.getString(R.string.msg_import_done, movieCount));
    actionBar.hide();
  }

  private void handleImportStartedMessage(Message msg) {
    actionBar.show();
    actionBar.setTitle("Importing filmography...");
    movieCount = msg.arg1;
    importedCount = 0;
  }

  private void handleMovieImportedMessage(Message msg) {
    importedCount++;
    Movie movie = (Movie) msg.getData().get("movie");
    if (!actionBar.isShowing()) {
      actionBar.show();
    }

    actionBar.setSubtitle(movie.getTitle());

    int percentageImported = (int) Math.min(100, 100 * ((float) importedCount / movieCount));
    if (percentageImported > 0) {
      int progress = (Window.PROGRESS_END - Window.PROGRESS_START) / 100 * percentageImported;
      activity.setSupportProgress(progress);
    }
  }
}