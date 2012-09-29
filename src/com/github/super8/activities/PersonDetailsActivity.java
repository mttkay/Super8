package com.github.super8.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.github.super8.R;
import com.github.super8.fragments.PersonDetailsFragment;
import com.github.super8.model.Movie;
import com.github.super8.model.Person;
import com.google.inject.Inject;

public class PersonDetailsActivity extends RoboSherlockFragmentActivity {

  @Inject private PersonDetailsFragment fragment;

  public static void launch(Context context, Movie movie) {
    Intent intent = new Intent(context, PersonDetailsActivity.class);
    intent.putExtra(Person.EXTRA_KEY, movie);
    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.person_details_activity);

    if (savedInstanceState == null) {
      Person person = getIntent().getParcelableExtra(Person.EXTRA_KEY);
      Bundle args = new Bundle();
      args.putParcelable(Person.EXTRA_KEY, person);
      fragment.setArguments(args);

      FragmentManager fm = getSupportFragmentManager();
      FragmentTransaction tx = fm.beginTransaction();
      tx.add(R.id.person_details_fragment, fragment);
      tx.commit();
    }
  }

}
