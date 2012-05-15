package com.github.super8.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.github.super8.R;
import com.github.super8.fragments.MovieDetailsFragment;
import com.github.super8.model.Movie;

public class MovieDetailsActivity extends RoboSherlockFragmentActivity {

  public static void launch(Context context, Movie movie) {
    Intent intent = new Intent(context, MovieDetailsActivity.class);
    intent.putExtra(MovieDetailsFragment.MOVIE_EXTRA, movie);
    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.movie_details_activity);

    if (savedInstanceState == null) {
      Movie movie = getIntent().getParcelableExtra(MovieDetailsFragment.MOVIE_EXTRA);

      MovieDetailsFragment fragment = MovieDetailsFragment.instantiate(movie);
      FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
      tx.add(R.id.movie_details_fragment, fragment);
      tx.commit();
    }
  }

}
