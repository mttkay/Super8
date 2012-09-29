package com.github.super8.fragments;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.github.ignition.core.widgets.RemoteImageView;
import com.github.super8.R;
import com.github.super8.behavior.ActsAsHomeScreen;
import com.github.super8.db.LibraryManager;
import com.github.super8.model.Person;
import com.github.super8.services.ImportFilmographyService;
import com.google.inject.Inject;

public class PersonDetailsFragment extends RoboFragment {

  public static final String TAG = PersonDetailsFragment.class.getSimpleName();

  public static final int MSG_MOVIE_IMPORTED = 0;
  public static final int MSG_IMPORT_STARTED = 1;
  public static final int MSG_IMPORT_SUCCEEDED = 2;
  public static final int MSG_IMPORT_FAILED = 3;

  private Person person;

  @Inject private LibraryManager library;

  @InjectView(R.id.person_image) RemoteImageView personImageView;
  @InjectView(R.id.person_details_like_button) ToggleButton likeButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      this.person = getArguments().getParcelable(Person.EXTRA_KEY);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.person_details_fragment, null);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (person == null) {
      // TODO: show empty view or something
      return;
    }
    String imageUrl = person.getScaledImageUrl(getActivity());
    if (imageUrl != null) {
      personImageView.setImageUrl(imageUrl);
      personImageView.loadImage();
    } else {
      // TODO: show dummy image
    }

    likeButton.setChecked(library.hasPerson(person));
    likeButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          library.savePerson(person);
          Intent intent = new Intent(getActivity(), ImportFilmographyService.class);
          intent.putExtra(ImportFilmographyService.PERSON_ID_EXTRA, person.getTmdbId());
          Messenger messenger = new Messenger(new Handler(getHomeScreen()
              .getImportFilmographyHandler()));
          intent.putExtra(ImportFilmographyService.MESSENGER_EXTRA, messenger);
          getActivity().startService(intent);
        } else {
          library.deletePerson(person);
        }
      }
    });
  }

  private ActsAsHomeScreen getHomeScreen() {
    return (ActsAsHomeScreen) getActivity();
  }
}
