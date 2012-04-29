package com.github.super8.actionbar;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.ActionMode.Callback;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class WatchlistActionMode implements Callback {

  private ActionBar actionBar;
  
  public WatchlistActionMode(ActionBar actionBar) {
    this.actionBar = actionBar;
  }
  
  @Override
  public boolean onActionItemClicked(ActionMode arg0, MenuItem arg1) {
    return true;
  }

  @Override
  public boolean onCreateActionMode(ActionMode mode, Menu menu) {
    menu.add("Open").setIcon(android.R.drawable.ic_menu_info_details)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    menu.add("Delete").setIcon(android.R.drawable.ic_menu_delete)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    return true;
  }

  @Override
  public void onDestroyActionMode(ActionMode arg0) {
    actionBar.hide();
  }

  @Override
  public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
    mode.setTitle("Edit watchlist item");
    mode.setSubtitle("Use this to see details or remove items");
    return true;
  }

}
