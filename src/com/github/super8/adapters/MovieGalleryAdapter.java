package com.github.super8.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.ignition.core.widgets.RemoteImageView;
import com.github.super8.R;
import com.github.super8.db.LibraryManager;
import com.github.super8.model.Movie;
import com.github.super8.support.Fonts;

public class MovieGalleryAdapter extends BaseAdapter {

  private LibraryManager library;
  private List<Movie> movies;
  private LayoutInflater inflater;
  private Context appContext;

  public MovieGalleryAdapter(Activity activity, LibraryManager library) {
    this.library = library;
    this.movies = library.getWatchlist();
    this.inflater = activity.getLayoutInflater();
    this.appContext = activity.getApplicationContext();
  }

  @Override
  public int getCount() {
    return movies.size();
  }

  @Override
  public Movie getItem(int position) {
    return movies.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public void notifyDataSetChanged() {
    this.movies = library.getWatchlist();
    super.notifyDataSetChanged();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder = null;
    if (convertView != null) {
      viewHolder = (ViewHolder) convertView.getTag();
    } else {
      convertView = inflater.inflate(R.layout.movie_compact, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.movieTitle = (TextView) convertView.findViewById(R.id.movie_compact_title);
      viewHolder.movieTitle.setTypeface(Fonts.robotoThin(appContext));
      viewHolder.movieTitle.setVisibility(View.VISIBLE);
      viewHolder.movieImage = (RemoteImageView) convertView.findViewById(R.id.movie_compact_poster);
      convertView.setTag(viewHolder);
    }

    Movie movie = getItem(position);

    viewHolder.movieTitle.setText(movie.getTitle());

    String imageUrl = movie.getScaledImageUrl(appContext);
    if (imageUrl != null) {
      viewHolder.movieImage.setImageUrl(imageUrl);
      viewHolder.movieImage.loadImage();
    } else {
      // TODO: show dummy image
    }

    return convertView;
  }

  static final class ViewHolder {
    TextView movieTitle;
    RemoteImageView movieImage;
  }
}
