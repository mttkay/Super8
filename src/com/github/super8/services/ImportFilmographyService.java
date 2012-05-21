package com.github.super8.services;

import roboguice.service.RoboIntentService;
import android.content.Intent;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.github.super8.db.LibraryManager;
import com.github.super8.fragments.PersonDetailsFragment;
import com.github.super8.model.Appearance;
import com.github.super8.model.Credits;
import com.github.super8.model.Movie;
import com.google.inject.Inject;

public class ImportFilmographyService extends RoboIntentService {

  public static final String PERSON_ID_EXTRA = "pid";
  public static final String MESSENGER_EXTRA = "msger";

  @Inject private TmdbApi tmdb;
  @Inject private LibraryManager library;

  public ImportFilmographyService() {
    super(ImportFilmographyService.class.getSimpleName());
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    int personId = intent.getIntExtra(PERSON_ID_EXTRA, 0);
    Messenger messenger = intent.getParcelableExtra(MESSENGER_EXTRA);
    try {
      Credits credits = tmdb.getCredits(personId);
      sendImportStartedMessage(messenger, credits);

      for (Appearance app : credits.getAllAppearances()) {
        // fetch the full movie record for this appearance
        Movie movie = tmdb.getMovie(app.getTmdbId());

        library.saveMovie(movie);

        sendMovieImportedMessage(messenger, movie);
      }

      sendImportSucceededMessage(messenger);

    } catch (Exception e) {
      handleError(messenger, e);
    }
  }

  private void sendImportSucceededMessage(Messenger messenger) throws RemoteException {
    Message message = Message.obtain();
    message.what = PersonDetailsFragment.MSG_IMPORT_SUCCEEDED;
    messenger.send(message);
  }

  private void sendMovieImportedMessage(Messenger messenger, Movie movie) throws RemoteException {
    Message message = Message.obtain();
    message.what = PersonDetailsFragment.MSG_MOVIE_IMPORTED;
    message.getData().putParcelable("movie", movie);
    messenger.send(message);
  }

  private void sendImportStartedMessage(Messenger messenger, Credits credits)
      throws RemoteException {
    Message message = Message.obtain();
    message.what = PersonDetailsFragment.MSG_IMPORT_STARTED;
    message.arg1 = credits.size();
    messenger.send(message);
  }

  private void handleError(Messenger messenger, Exception e) {
    e.printStackTrace();
    Message message = Message.obtain();
    message.what = PersonDetailsFragment.MSG_IMPORT_FAILED;
    message.getData().putString("error", e.getMessage());
    try {
      messenger.send(message);
    } catch (RemoteException re) {
      re.printStackTrace();
    }
  }

}