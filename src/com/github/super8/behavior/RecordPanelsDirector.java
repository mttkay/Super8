package com.github.super8.behavior;

import roboguice.activity.event.OnContentChangedEvent;
import roboguice.event.Observes;
import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectFragment;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.github.super8.R;
import com.github.super8.activities.HomeActivity;
import com.github.super8.activities.PersonDetailsActivity;
import com.github.super8.fragments.PersonDetailsFragment;
import com.github.super8.fragments.PersonFinderFragment;
import com.github.super8.model.Person;
import com.google.inject.Inject;

@ContextSingleton
public class RecordPanelsDirector implements RecordingBehavior {

  private HomeActivity activity;
  private RecordingBehavior behavior;

  @InjectFragment(R.id.person_finder_fragment) private PersonFinderFragment personFinderFragment;
  @InjectView(R.id.record_mode_layout) private ViewGroup layout;

  @Inject
  public RecordPanelsDirector(Activity activity) {
    this.activity = (HomeActivity) activity;
  }

  public void initialize(@Observes OnContentChangedEvent event) {
    int orientation = activity.getResources().getConfiguration().orientation;
    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
      this.behavior = new LandscapeBehavior(activity, layout);
    } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
      this.behavior = new PortraitBehavior(activity, layout);
    }
    personFinderFragment.setRecordPanelsDirector(this);
  }

  public boolean onBackPressed() {
    return behavior.onBackPressed();
  }

  public void show(boolean show) {
    if (show) {
      layout.setVisibility(View.VISIBLE);
    } else {
      layout.setVisibility(View.GONE);
    }
    behavior.show(show);
  }

  @Override
  public void onPersonSelected(Person person) {
    behavior.onPersonSelected(person);
  }
}

class PortraitBehavior implements RecordingBehavior {
  private HomeActivity activity;

  public PortraitBehavior(HomeActivity activity, ViewGroup container) {
    this.activity = activity;
  }

  @Override
  public void show(boolean show) {
  }

  @Override
  public void onPersonSelected(Person person) {
    Intent intent = new Intent(activity, PersonDetailsActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.putExtra(Person.EXTRA_KEY, person);
    activity.startActivity(intent);
  }

  @Override
  public boolean onBackPressed() {
    return false;
  }
}

class LandscapeBehavior implements RecordingBehavior {
  private HomeActivity activity;
  private ViewGroup layout;

  public LandscapeBehavior(HomeActivity activity, ViewGroup layout) {
    this.activity = activity;
    this.layout = layout;
  }

  @Override
  public void show(boolean show) {

  }

  @Override
  public void onPersonSelected(Person person) {
    View fragmentContainer = layout.findViewById(R.id.person_details_fragment);

    FragmentManager fragmentManager = activity.getSupportFragmentManager();
    FragmentTransaction tx = fragmentManager.beginTransaction();

    Fragment fragment = new PersonDetailsFragment();
    Bundle bundle = new Bundle();
    person.addTo(bundle);
    fragment.setArguments(bundle);

    tx.replace(R.id.person_details_fragment, fragment);

    if (fragmentContainer.getVisibility() == View.GONE) {
      fragmentContainer.setVisibility(View.VISIBLE);
      tx.addToBackStack(null);
    }

    tx.commit();
  }

  @Override
  public boolean onBackPressed() {
    layout.findViewById(R.id.person_details_fragment).setVisibility(View.GONE);
    return false;
  }
}
