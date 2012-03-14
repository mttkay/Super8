package com.github.super8.apis.imdb;

import android.content.Context;

public class ImdbGetMovieTask extends ImdbApiTask<ImdbMovie> {

    private String title;

    protected Context context;

    public ImdbGetMovieTask(String title) {
        this.title = title;
    }
//
//    @Override
//    public ImdbMovie call() throws Exception {
//        return api.findByTitle(title);
//    }
//
//    @Override
//    protected void onSuccess(ImdbMovie movie) throws Exception {
//        Context context = contextProvider.get();
//        Toast.makeText(context, movie.getTitle(), Toast.LENGTH_LONG).show();
//    }
}
