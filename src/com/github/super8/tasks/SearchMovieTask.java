/**
 * 
 */
package com.github.super8.tasks;

import java.util.List;

import android.widget.EditText;

import com.github.ignition.core.dialogs.IgnitedDialogs;
import com.github.ignition.core.tasks.IgnitedAsyncTask;
import com.github.super8.R;
import com.github.super8.Super8Application;
import com.github.super8.activities.DashboardActivity;
import com.github.super8.apis.ServerCommunicationException;
import com.github.super8.apis.tmdb.v2.TmdbApi;
import com.github.super8.model.Movie;
import com.github.super8.support.ViewHelper;

public final class SearchMovieTask extends
    IgnitedAsyncTask<DashboardActivity, String, Void, List<Movie>> {

  private TmdbApi api;

  public SearchMovieTask(DashboardActivity activity) {
    connect(activity);
    Super8Application app = (Super8Application) activity.getApplication();
    //api = new TmdbApi(app.getIgnitedHttp());
  }

  @Override
  protected List<Movie> run(String... params) throws ServerCommunicationException {
    String query = params[0];
    return api.search(query);
  }

  @Override
  public void onTaskCompleted(DashboardActivity context, List<Movie> result) {
    EditText searchInput = (EditText) context.findViewById(R.id.dashboard_search_input);
    searchInput.setEnabled(true);
    ViewHelper.showProgressSpinner(context, false);
  }

  @Override
  public void onTaskSuccess(DashboardActivity activity, List<Movie> result) {
    activity.onSearchComplete(result);
  }

  @Override
  public void onTaskFailed(DashboardActivity activity, Exception error) {
    super.onTaskFailed(activity, error);
    IgnitedDialogs.newErrorDialog(activity, "Ooops!", error).show();
  }
}