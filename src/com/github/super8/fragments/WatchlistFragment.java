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

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.github.super8.R;
import com.github.super8.actionbar.WatchlistActionMode;
import com.github.super8.adapters.MovieGalleryAdapter;
import com.github.super8.db.LibraryManager;
import com.google.inject.Inject;

public class WatchlistFragment extends RoboFragment implements OnItemClickListener,
    OnItemSelectedListener {

  @Inject private LibraryManager library;

  @InjectView(R.id.gallery) private Gallery gallery;

  private MovieGalleryAdapter adapter;
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
  }

  public void refresh() {
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    SherlockFragmentActivity activity = (SherlockFragmentActivity) getActivity();
    actionMode = activity.startActionMode(new WatchlistActionMode(activity.getSupportActionBar()));
  }

  @Override
  public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    if (actionMode != null) {
      actionMode.finish();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
  }
}
