package com.github.super8.fragments;

import java.util.List;

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
import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.github.super8.apis.tmdb.v3.TmdbApiHandler;
import com.github.super8.model.Person;
import com.google.inject.Inject;

public class LearnMoviesFragment extends TaskManagingFragment<List<Person>> implements
    TmdbApiHandler<List<Person>>, TextWatcher, Handler.Callback {

  private static final int QUERY_DELAY = 500;
  private static final int QUERY_READY_MSG = 0;
  private static final int TASK_SEARCH_PERSON = 0;

  @Inject private TmdbApi tmdb;

  @InjectView(android.R.id.list) private ListView listView;
  @InjectView(android.R.id.edit) private EditText searchField;
  @InjectView(android.R.id.progress) private ProgressBar progressSpinner;

  private PersonListAdapter adapter;
  private Handler queryReadyHandler;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    queryReadyHandler = new Handler(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.learn_movies_fragment, container);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    adapter = new PersonListAdapter(getActivity());
    listView.setAdapter(adapter);

    searchField.addTextChangedListener(this);
  }

  public void lookupPerson() {
    String query = searchField.getText().toString().trim();
    if (!TextUtils.isEmpty(query)) {
      addTask(TASK_SEARCH_PERSON, tmdb.searchPerson(this, query));
    } else {
      adapter.clear();
    }
  }

  @Override
  public void onTaskStarted(Context context) {
    progressSpinner.setVisibility(View.VISIBLE);
  }

  @Override
  public void onTaskSuccess(Context context, List<Person> result) {
    adapter.clear();
    for (Person person : result) {
      adapter.add(person);
    }
  }

  @Override
  public void onTaskCompleted(Context context, List<Person> result) {
    progressSpinner.setVisibility(View.GONE);
  }

  @Override
  public void afterTextChanged(Editable s) {
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
}
