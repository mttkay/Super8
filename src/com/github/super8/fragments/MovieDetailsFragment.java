package com.github.super8.fragments;

import java.util.List;

import roboguice.inject.InjectView;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.github.ignition.core.widgets.RemoteImageView;
import com.github.ignition.core.widgets.ScrollingTextView;
import com.github.super8.R;
import com.github.super8.adapters.TabSwitchingPagerAdapter;
import com.github.super8.adapters.TabSwitchingPagerAdapter.OnTabViewAvailableListener;
import com.github.super8.apis.tmdb.v3.DefaultTmdbApiHandler;
import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.github.super8.apis.tmdb.v3.TmdbApiHandler;
import com.github.super8.behavior.ActionBarAware;
import com.github.super8.model.CastAppearance;
import com.github.super8.model.Credits;
import com.github.super8.model.CrewAppearance;
import com.github.super8.model.Movie;
import com.github.super8.support.Fonts;
import com.google.inject.Inject;

public class MovieDetailsFragment extends TaskManagingFragment<Movie> implements ActionBarAware,
    OnTabViewAvailableListener, TmdbApiHandler<Movie> {

  public static final String MOVIE_EXTRA = "movie";

  private static final int TASK_GET_MOVIE = 0;
  private static final int TASK_GET_CREDITS = 1;

  @InjectView(R.id.pager) private ViewPager pager;

  @Inject private TmdbApi tmdb;

  private TabSwitchingPagerAdapter adapter;
  private View overviewTab, creditsTab;

  private Movie movie;

  public static void launch(FragmentManager fm, MovieDetailsFragment fragment, Movie movie) {
    Bundle args = new Bundle();
    args.putParcelable(MOVIE_EXTRA, movie);
    fragment.setArguments(args);

    FragmentTransaction tx = fm.beginTransaction();
    tx.add(R.id.movie_details_fragment, fragment);
    tx.commit();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    movie = getArguments().getParcelable(MOVIE_EXTRA);

    addTask(TASK_GET_MOVIE, tmdb.backfillMovie(this, movie));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.movie_details_fragment, null);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    ActionBar actionBar = getActionBar();
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    actionBar.setDisplayShowHomeEnabled(false);
    actionBar.setTitle(movie.getTitle());

    adapter = new TabSwitchingPagerAdapter(getActionBar(), pager);
    adapter.addOnTabViewAvailableListener(this);

    adapter.addTab(actionBar.newTab().setText("Overview"), R.layout.movie_details_overview);
  }

  @Override
  public ActionBar getActionBar() {
    return ((SherlockFragmentActivity) getActivity()).getSupportActionBar();
  }

  @Override
  public void onTabViewAvailable(int position, View tabView) {
    if (position == 0) {
      setupOverviewTab(tabView);
    } else if (position == 1) {
      setupCreditsTab(tabView);
    }
  }

  private void setupCreditsTab(View tabView) {
    this.creditsTab = tabView;
  }

  private void setupOverviewTab(View tabView) {
    this.overviewTab = tabView;
    RemoteImageView moviePoster = (RemoteImageView) tabView.findViewById(R.id.movie_details_poster);
    String posterUrl = movie.getScaledImageUrl(getActivity());
    if (posterUrl != null) {
      moviePoster.setImageUrl(posterUrl);
      moviePoster.loadImage();
    } else {
      // TODO
    }

  }

  @Override
  public boolean onTaskSuccess(Context context, Movie movie) {
    addTask(TASK_GET_CREDITS, tmdb.getCasts(new MovieCreditsHandler(), movie.getTmdbId()));

    populateSynopsis(movie);
    return true;
  }

  private void populateCredits(Credits credits) {
    adapter.addTab(getActionBar().newTab().setText("Credits"), R.layout.movie_details_credits);
    ScrollingTextView creditsText = (ScrollingTextView) creditsTab.findViewById(android.R.id.text1);
    creditsText.setSpeed(20);
    creditsText.setTypeface(Fonts.robotoThin(getActivity()));

    SpannableStringBuilder sb = new SpannableStringBuilder();
    sb.append("A film by\n\n");
    sb.append(getDirectorText(credits));
    sb.append("\n\n\n");
    sb.append(getHeaderString("Producers\n\n"));
    sb.append(getCrewAppearanceString(credits
        .getCrewAppearances(CrewAppearance.DEPARTMENT_PRODUCTION)));
    sb.append("\n\n\n");
    sb.append(getHeaderString("Writers\n\n"));
    sb.append(getCrewAppearanceString(credits.getCrewAppearances(CrewAppearance.DEPARTMENT_WRITING)));
    sb.append("\n\n\n");
    sb.append(getHeaderString("Starring\n\n"));
    sb.append(getCastAppearanceString(credits.getCastAppearances()));

    creditsText.setText(sb, BufferType.SPANNABLE);
  }

  private Spannable getDirectorText(Credits credits) {
    String directors = TextUtils.join("\n",
        credits.getCrewAppearances(CrewAppearance.DEPARTMENT_DIRECTING));
    SpannableString text = new SpannableString(directors);
    text.setSpan(new TextAppearanceSpan(getActivity(), R.style.TextAppearance_Credits_Gold), 0,
        directors.length(), 0);
    // text.setSpan(new ForegroundColorSpan(Color.), 0, directors.length(), 0);
    return text;
  }

  private Spannable getHeaderString(String text) {
    SpannableString spanned = new SpannableString(text);
    // spanned.setSpan(new UnderlineSpan(), 0, text.length(), 0);
    spanned.setSpan(new TypefaceSpan("serif"), 0, text.length(), 0);
    return spanned;
  }

  private Spannable getCrewAppearanceString(List<CrewAppearance> appearances) {
    String source = TextUtils.join("\n", appearances);
    SpannableString spanned = new SpannableString(source);
    spanned.setSpan(new TextAppearanceSpan(getActivity(), R.style.TextAppearance_Credits_Gold), 0,
        source.length(), 0);
    return spanned;
  }

  private Spannable getCastAppearanceString(List<CastAppearance> appearances) {
    SpannableStringBuilder sb = new SpannableStringBuilder();
    for (CastAppearance castAppearance : appearances) {
      String actor = castAppearance.getName();
      SpannableString actorSpanned = new SpannableString(actor);
      actorSpanned.setSpan(new TextAppearanceSpan(getActivity(),
          R.style.TextAppearance_Credits_Gold), 0, actor.length(), 0);
      sb.append(actorSpanned);

      sb.append("\n");

      String character = "as " + castAppearance.getCharacter();
      SpannableString characterSpanned = new SpannableString(character);
      characterSpanned.setSpan(new TextAppearanceSpan(getActivity(),
          R.style.TextAppearance_Credits_Small), 0, character.length(), 0);
      sb.append(characterSpanned);

      sb.append("\n\n");
    }
    return sb;
  }

  private void populateSynopsis(Movie movie) {
    String synopsis = movie.getSynopsis();
    if (synopsis != null) {
      View summarySection = ((ViewStub) overviewTab
          .findViewById(R.id.movie_details_summary_section)).inflate();
      ((TextView) summarySection.findViewById(android.R.id.text1)).setText("Synopsis");
      ((TextView) summarySection.findViewById(android.R.id.text2)).setText(synopsis);
    }
  }

  private class MovieCreditsHandler extends DefaultTmdbApiHandler<Credits> {

    public MovieCreditsHandler() {
      super(getActivity());
    }

    @Override
    public boolean onTaskSuccess(Context context, Credits credits) {
      populateCredits(credits);
      return true;
    }
  }
}
