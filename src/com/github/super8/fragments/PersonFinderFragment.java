package com.github.super8.fragments;

import java.util.List;

import roboguice.fragment.RoboListFragment;
import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.super8.R;
import com.github.super8.adapters.PersonListAdapter;
import com.github.super8.apis.tmdb.v3.DefaultTmdbApiHandler;
import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.github.super8.apis.tmdb.v3.TmdbApiHandler;
import com.github.super8.model.Person;
import com.github.super8.tasks.TaskManager;
import com.google.inject.Inject;

public class PersonFinderFragment extends RoboListFragment implements TmdbApiHandler<List<Person>>,
    TextWatcher, Handler.Callback {

  private static final int QUERY_DELAY = 500;
  private static final int QUERY_READY_MSG = 0;
  private static final int TASK_SEARCH_PERSON = 0;
  private static final int TASK_GET_PERSON_DETAILS = 1;

  @Inject private TaskManager taskManager;
  @Inject private TmdbApi tmdb;

  @InjectView(android.R.id.list) private ListView listView;
  @InjectView(android.R.id.empty) private TextView emptyView;
  @InjectView(android.R.id.edit) private EditText searchField;
  @InjectView(android.R.id.progress) private ProgressBar progressSpinner;

  private PersonListAdapter adapter;
  private Handler queryReadyHandler;

  public PersonFinderFragment() {
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
    return inflater.inflate(R.layout.person_finder_fragment, null);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    adapter = new PersonListAdapter(getActivity());
    setListAdapter(adapter);

    LayoutAnimationController listViewAnim = new LayoutAnimationController(
        AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
    listView.setLayoutAnimation(listViewAnim);

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

    TextView personName = (TextView) v.findViewById(android.R.id.text1);
    personName.setText(R.string.person_loading);

    Person person = adapter.getItem(position);
    taskManager.registerTask(TASK_GET_PERSON_DETAILS,
        tmdb.getPerson(new GetPersonHandler(getActivity()), person.getTmdbId()));

    // adapter.clear();
    // adapter.add(person);

    // adapter.notifyDataSetChanged();
  }

  public void lookupPerson() {
    String query = searchField.getText().toString().trim();
    if (!TextUtils.isEmpty(query)) {
      emptyView.setText(R.string.empty_list);
      taskManager.registerTask(TASK_SEARCH_PERSON, tmdb.searchPerson(this, query));
    } else {
      emptyView.setText(null);
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
    InputMethodManager imm = (InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
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

  static class GetPersonHandler extends DefaultTmdbApiHandler<Person> {

    public GetPersonHandler(Context context) {
      super(context);
    }

    @Override
    public boolean onTaskSuccess(Context context, Person person) {
      FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
      FragmentTransaction tx = fragmentManager.beginTransaction();
      // tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      PersonDetailsFragment fragment = new PersonDetailsFragment();
      Bundle args = new Bundle();
      args.putParcelable(PersonDetailsFragment.PERSON_EXTRA, person);
      fragment.setArguments(args);
      tx.replace(R.id.drawer_content, fragment);
      tx.addToBackStack(null);
      tx.commit();
      return true;
    }
  }
}
