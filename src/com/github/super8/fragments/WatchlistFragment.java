package com.github.super8.fragments;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.github.super8.R;
import com.github.super8.activities.MovieDetailsActivity;
import com.github.super8.adapters.MovieGalleryAdapter;
import com.github.super8.db.LibraryManager;
import com.github.super8.model.Movie;
import com.google.inject.Inject;

public class WatchlistFragment extends RoboFragment implements OnItemClickListener,
    OnItemSelectedListener, ActionMode.Callback {

  @Inject private LibraryManager library;

  @InjectView(R.id.watchlist_layout) private ViewGroup container;
  @InjectView(R.id.gallery) private Gallery gallery;
  @InjectView(android.R.id.empty) private View emptyView;

  private MovieGalleryAdapter adapter;
  private Movie currentMovie;
  private ActionMode actionMode;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.watchlist_fragment, null);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adapter = new MovieGalleryAdapter(getActivity(), library);
    gallery.setAdapter(adapter);
    gallery.setOnItemClickListener(this);
    gallery.setOnItemSelectedListener(this);
    
    TextView helpText1 = (TextView) emptyView.findViewById(android.R.id.text1);
    helpText1.setText(R.string.help_text_empty_watchlist_1);
    TextView helpText2 = (TextView) emptyView.findViewById(android.R.id.text2);
    helpText2.setText(R.string.help_text_empty_watchlist_2);
  }

  public void refresh() {
    if (library.hasWatchlistItems()) {
      adapter.notifyDataSetChanged();
      gallery.setVisibility(View.VISIBLE);
      emptyView.setVisibility(View.GONE);
    } else {
      gallery.setVisibility(View.GONE);
      emptyView.setVisibility(View.VISIBLE);
    }
  }

  public void show(boolean show) {
    if (show) {
      container.setVisibility(View.VISIBLE);
      refresh();
    } else {
      container.setVisibility(View.GONE);
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    SherlockFragmentActivity activity = (SherlockFragmentActivity) getActivity();
    currentMovie = adapter.getItem(position);
    if (actionMode != null) {
      stopActionMode();
    } else {
      actionMode = activity.startActionMode(this);
    }
  }

  private void stopActionMode() {
    actionMode.finish();
    actionMode = null;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    currentMovie = adapter.getItem(position);
    if (actionMode != null) {
      stopActionMode();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
  }

  @Override
  public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
    switch (item.getItemId()) {
    case R.id.menu_watchlist_details:
      MovieDetailsActivity.launch(getActivity(), currentMovie);
      break;
    case R.id.menu_watchlist_remove:
      library.removeFromWatchlist(currentMovie);
      onDestroyActionMode(mode);
      adapter.notifyDataSetChanged();
      break;
    }
    return true;
  }

  @Override
  public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    int defaultFlags = MenuItem.SHOW_AS_ACTION_WITH_TEXT;
    menu.add(Menu.NONE, R.id.menu_watchlist_details, Menu.NONE, R.string.menu_watchlist_details)
        .setIcon(android.R.drawable.ic_menu_info_details)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | defaultFlags);
    menu.add(Menu.NONE, R.id.menu_watchlist_remove, Menu.NONE, R.string.menu_watchlist_remove)
        .setIcon(android.R.drawable.ic_menu_delete)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | defaultFlags);
    return true;
  }

  @Override
  public void onDestroyActionMode(ActionMode mode) {
    SherlockFragmentActivity activity = (SherlockFragmentActivity) getActivity();
    activity.getSupportActionBar().hide();
  }

  @Override
  public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
    mode.setTitle("Edit watchlist item");
    mode.setSubtitle("Use this to see details or remove items");
    return true;
  }
}
