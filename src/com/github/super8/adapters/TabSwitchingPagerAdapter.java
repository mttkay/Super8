package com.github.super8.adapters;

import java.util.LinkedList;
import java.util.List;

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

public class TabSwitchingPagerAdapter extends PagerAdapter implements OnPageChangeListener,
    TabListener {

  public interface OnTabViewAvailableListener {
    void onTabViewAvailable(int position, View tabView);
  }

  private static final class TabWrapper {
    private TabWrapper(Tab tab, int tabViewLayoutId) {
      this.tab = tab;
      this.tabViewLayoutId = tabViewLayoutId;
    }

    private Tab tab;
    private int tabViewLayoutId;
  }

  private ActionBar actionBar;
  private ViewPager pager;
  private List<TabWrapper> tabs = new LinkedList<TabWrapper>();
  private List<OnTabViewAvailableListener> tabAvailableListeners = new LinkedList<OnTabViewAvailableListener>();

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

  public void addOnTabViewAvailableListener(OnTabViewAvailableListener listener) {
    tabAvailableListeners.add(listener);
  }

  public void removeOnTabViewAvailableListener(OnTabViewAvailableListener listener) {
    tabAvailableListeners.remove(listener);
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
    TabSwitchingPagerAdapter.TabWrapper wrapper = tabs.get(position);

    LayoutInflater inflater = LayoutInflater.from(container.getContext());
    View tabView = inflater.inflate(wrapper.tabViewLayoutId, container, false);
    tabView.setTag(position);
    container.addView(tabView);

    for (OnTabViewAvailableListener listener : tabAvailableListeners) {
      listener.onTabViewAvailable(position, tabView);
    }

    return wrapper;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    int viewPosition = (Integer) view.getTag();
    int tabPosition = (Integer) ((TabSwitchingPagerAdapter.TabWrapper) object).tab.getTag();
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