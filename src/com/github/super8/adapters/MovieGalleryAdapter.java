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
import com.github.super8.model.Movie;

public class MovieGalleryAdapter extends BaseAdapter {

  private List<Movie> movies;
  private LayoutInflater inflater;
  private Context appContext;

  public MovieGalleryAdapter(Activity activity, List<Movie> movies) {
    this.movies = movies;
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
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder viewHolder = null;
    if (convertView != null) {
      viewHolder = (ViewHolder) convertView.getTag();
    } else {
      convertView = inflater.inflate(R.layout.movie_gallery_item, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.movieTitle = (TextView) convertView.findViewById(R.id.movie_gallery_item_title);
      viewHolder.movieImage = (RemoteImageView) convertView
          .findViewById(R.id.movie_gallery_item_image);
      convertView.setTag(viewHolder);
    }
    
    Movie movie = getItem(position);
    viewHolder.movieTitle.setText(movie.getTitle());
    viewHolder.movieImage.setImageUrl(movie.getScaledImageUrl(appContext));
    viewHolder.movieImage.loadImage();
    
    return convertView;
  }

  static final class ViewHolder {
    TextView movieTitle;
    RemoteImageView movieImage;
  }
}
