package com.github.super8.activities;

import java.util.List;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.github.super8.R;
import com.github.super8.R.id;
import com.github.super8.R.layout;
import com.github.super8.adapters.MovieListAdapter;
import com.github.super8.model.Movie;
import com.github.super8.support.ViewHelper;
import com.github.super8.tasks.SearchMovieTask;

public class DashboardActivity extends RoboListActivity {

  private ListView resultList;
  private MovieListAdapter adapter;

  private SearchMovieTask movieLookupTask;

//  @InjectView(R.id.ign_horizontal_pager)
//  private HorizontalPager pager;

  @InjectView(R.id.dashboard_search_input)
  private EditText searchInput;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.dashboard);

    resultList = getListView();
    adapter = new MovieListAdapter(this);
    setListAdapter(adapter);

    movieLookupTask = (SearchMovieTask) getLastNonConfigurationInstance();
  }

  @Override
  protected void onRestoreInstanceState(Bundle state) {
    super.onRestoreInstanceState(state);
    if (movieLookupTask != null) {
      movieLookupTask.connect(this);
      if (movieLookupTask.isRunning()) {
        prepareMovieLookupTask();
      }
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (movieLookupTask != null) {
      movieLookupTask.disconnect();
    }
  }

  @Override
  public Object onRetainNonConfigurationInstance() {
    return movieLookupTask;
  }

  public void onTitleSearchButtonClick(View view) {
    prepareMovieLookupTask();
    movieLookupTask = new SearchMovieTask(this);
    movieLookupTask.execute(searchInput.getText().toString());
  }

  private void prepareMovieLookupTask() {
    searchInput.setEnabled(false);
    ViewHelper.showProgressSpinner(this, true);
  }

  public void onSearchComplete(List<Movie> movies) {
    adapter.clear();
    adapter.addAll(movies);

    //pager.scrollRight();
  }

//  @Override
//  protected void onListItemClick(ListView l, View v, int position, long id) {
//    super.onListItemClick(l, v, position, id);
//
//    // ViewStub stub = (ViewStub) findViewById(R.id.dashboard_movie_details_stub);
//    // ViewGroup movieLayout = null;
//    // if (stub != null) {
//    // movieLayout = (ViewGroup) stub.inflate();
//    // } else {
//    // movieLayout = (ViewGroup) findViewById(R.id.dashboard_page_movie_details_layout);
//    // }
//
//    ViewGroup movieLayout = (ViewGroup) pager
//        .findViewById(R.id.dashboard_page_movie_details_layout);
//    if (movieLayout == null) {
//      movieLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.dashboard_page_movie_details,
//          pager);
//    }
//
//    Movie movie = adapter.getItem(position);
//
//    Typeface font = Fonts.loadFont(this, "adler.ttf");
//
//    TextView titleText = (TextView) movieLayout.findViewById(R.id.movie_details_title);
//    titleText.setTypeface(font);
//    titleText.setText(movie.getTitle());
//
//    String year = movie.getYear();
//    TextView yearText = (TextView) movieLayout.findViewById(R.id.movie_details_year);
//    if (year != null) {
//      yearText.setTypeface(font);
//      yearText.setText(movie.getYear());
//    } else {
//      yearText.setVisibility(View.GONE);
//    }
//
//    ImageView posterImage = (ImageView) movieLayout.findViewById(R.id.movie_details_poster_image);
//    String posterImageUrl = movie.getPosterImageUrl();
//    if (posterImageUrl != null) {
//      //ImageLoader.start(posterImageUrl, posterImage);
//    }
//
//    pager.scrollRight();
//  }
}