package com.github.super8.fragments;

import java.util.List;

import roboguice.fragment.RoboListFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.github.super8.R;
import com.github.super8.adapters.PersonListAdapter;
import com.github.super8.apis.tmdb.v3.DefaultTmdbApiHandler;
import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.github.super8.apis.tmdb.v3.TmdbApiHandler;
import com.github.super8.model.CastAppearance;
import com.github.super8.model.Credits;
import com.github.super8.model.CrewAppearance;
import com.github.super8.model.Person;
import com.github.super8.tasks.TaskManager;
import com.google.inject.Inject;

public class LearnMoviesFragment extends RoboListFragment implements TmdbApiHandler<List<Person>>,
    TextWatcher, Handler.Callback {

  static class GetCreditsHandler extends DefaultTmdbApiHandler<Credits> {

    public GetCreditsHandler(Context context) {
      super(context);
    }

    @Override
    public boolean onTaskSuccess(Context context, Credits result) {
      System.out.println("New credits: " + result.getTmdbId());
      System.out.println("CAST:");
      for (CastAppearance app : result.getCastAppearances()) {
        System.out.println(app.getMovieTitle());
      }
      System.out.println("CREW:");
      for (CrewAppearance app : result.getCrewAppearances()) {
        System.out.println(app.getMovieTitle());
      }
      return super.onTaskSuccess(context, result);
    }
  }

  private static final int QUERY_DELAY = 500;
  private static final int QUERY_READY_MSG = 0;
  private static final int TASK_SEARCH_PERSON = 0;
  private static final int TASK_GET_CREDITS = 1;

  @Inject private TaskManager taskManager;
  @Inject private TmdbApi tmdb;

  @InjectView(android.R.id.list) private ListView listView;
  @InjectView(android.R.id.edit) private EditText searchField;
  @InjectView(android.R.id.progress) private ProgressBar progressSpinner;

  private PersonListAdapter adapter;
  private Handler queryReadyHandler;

  public LearnMoviesFragment() {
    setRetainInstance(true);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    queryReadyHandler = new Handler(this);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    taskManager.reconnectTasks(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.learn_movies_fragment, container);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    adapter = new PersonListAdapter(getActivity());
    setListAdapter(adapter);

    searchField.addTextChangedListener(this);
  }

  @Override
  public void onDetach() {
    super.onDetach();
    taskManager.disconnectTasks();
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);

    Person person = adapter.getItem(position);
    taskManager.registerTask(TASK_GET_CREDITS,
        tmdb.getCredits(new GetCreditsHandler(getActivity()), person.getTmdbId()));
  }

  public void lookupPerson() {
    String query = searchField.getText().toString().trim();
    if (!TextUtils.isEmpty(query)) {
      taskManager.registerTask(TASK_SEARCH_PERSON, tmdb.searchPerson(this, query));
    } else {
      adapter.clear();
    }
  }

  @Override
  public boolean onTaskStarted(Context context) {
    progressSpinner.setVisibility(View.VISIBLE);
    return true;
  }

  @Override
  public boolean onTaskSuccess(Context context, List<Person> result) {
    adapter.clear();
    for (Person person : result) {
      adapter.add(person);
    }
    return true;
  }

  @Override
  public boolean onTaskCompleted(Context context, List<Person> result) {
    progressSpinner.setVisibility(View.GONE);
    return true;
  }

  @Override
  public boolean onTaskFailed(Context arg0, Exception arg1) {
    return false;
  }

  @Override
  public boolean onTaskProgress(Context arg0, Void... arg1) {
    return true;
  }

  @Override
  public void afterTextChanged(Editable s) {
    taskManager.cancelAllTasks();
    if (queryReadyHandler.hasMessages(QUERY_READY_MSG)) {
      queryReadyHandler.removeMessages(QUERY_READY_MSG);
    }
    queryReadyHandler.sendEmptyMessageDelayed(QUERY_READY_MSG, QUERY_DELAY);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  @Override
  public boolean handleMessage(Message msg) {
    lookupPerson();
    return true;
  }

  @Override
  public Context getContext() {
    return getActivity();
  }

  @Override
  public void setContext(Context arg0) {
  }
}
