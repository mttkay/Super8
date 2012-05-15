package com.github.super8.fragments;

import java.util.LinkedList;
import java.util.List;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.github.super8.R;
import com.github.super8.behavior.ActionBarAware;
import com.github.super8.model.Movie;

public class MovieDetailsFragment extends RoboSherlockFragment implements ActionBarAware {

  public static final String MOVIE_EXTRA = "movie";

  @InjectView(R.id.pager) private ViewPager pager;
  private TabSwitchingPagerAdapter adapter;

  private Movie movie;

  public static MovieDetailsFragment instantiate(Movie movie) {
    MovieDetailsFragment fragment = new MovieDetailsFragment();
    Bundle args = new Bundle();
    args.putParcelable(MOVIE_EXTRA, movie);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    movie = getArguments().getParcelable(MOVIE_EXTRA);
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
    adapter.addTab(actionBar.newTab().setText("Overview"), R.layout.movie_details_overview);
    adapter.addTab(actionBar.newTab().setText("Credits"), R.layout.movie_details_credits);
  }

  @Override
  public ActionBar getActionBar() {
    return ((SherlockFragmentActivity) getActivity()).getSupportActionBar();
  }

  static class TabSwitchingPagerAdapter extends PagerAdapter implements OnPageChangeListener,
      TabListener {

    private ActionBar actionBar;
    private ViewPager pager;
    private List<TabWrapper> tabs = new LinkedList<TabWrapper>();

    private static final class TabWrapper {
      private TabWrapper(Tab tab, int tabViewLayoutId) {
        this.tab = tab;
        this.tabViewLayoutId = tabViewLayoutId;
      }

      private Tab tab;
      private int tabViewLayoutId;
    }

    public TabSwitchingPagerAdapter(ActionBar actionBar, ViewPager pager) {
      this.actionBar = actionBar;
      this.pager = pager;
      pager.setAdapter(this);
      pager.setOnPageChangeListener(this);
    }

    @Override
    public int getCount() {
      return tabs.size();
    }

    public void addTab(Tab tab, int tabViewLayoutId) {
      int tabPosition = tabs.size();
      tab.setTabListener(this);
      tab.setTag(tabPosition);

      actionBar.addTab(tab);
      tabs.add(new TabWrapper(tab, tabViewLayoutId));

      notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
      TabWrapper wrapper = tabs.get(position);

      LayoutInflater inflater = LayoutInflater.from(container.getContext());
      View tabView = inflater.inflate(wrapper.tabViewLayoutId, container, false);
      tabView.setTag(position);
      container.addView(tabView);

      return wrapper;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      int viewPosition = (Integer) view.getTag();
      int tabPosition = (Integer) ((TabWrapper) object).tab.getTag();
      return viewPosition == tabPosition;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction tx) {
      int tabPosition = (Integer) tab.getTag();
      pager.setCurrentItem(tabPosition);
    }

    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
      actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

  }
}
