package com.github.super8.fragments;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import com.github.super8.R;
import com.github.super8.adapters.MovieGalleryAdapter;
import com.github.super8.db.LibraryManager;
import com.google.inject.Inject;

public class WatchlistFragment extends RoboFragment {

  @Inject private LibraryManager library;

  @InjectView(R.id.gallery) private Gallery gallery;
  
  private MovieGalleryAdapter adapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.watchlist_fragment, null);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adapter = new MovieGalleryAdapter(getActivity(), library.getWatchlist());
    gallery.setAdapter(adapter);
    adapter.notifyDataSetChanged();
  }
}
