package com.github.super8.apis.imdb;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.ignition.support.http.IgnitedHttpResponse;

public class ImdbApi {

    public static final String ENDPOINT = "http://imdbapi.com";

    public ImdbMovie findByTitle(String title) throws ConnectException {
        IgnitedHttpResponse resp = httpGet(ENDPOINT + "?t=" + URLEncoder.encode(title));

        try {
            ImdbMovie movie = parseMovie(resp.getResponseBodyAsString());

            return movie;
        } catch (IOException e) {
            throw new ConnectException(e.getMessage());
        }
    }

    private IgnitedHttpResponse httpGet(String url) throws ConnectException {
        //return IgnitedHttp.get(url).expecting(200).retries(3).send();
        return null;
    }

    private ImdbMovie parseMovie(String json) throws ConnectException {
        try {
            JSONObject root = new JSONObject(json);
            ImdbMovie movie = new ImdbMovie();
            movie.setId(root.getString("ID"));
            movie.setTitle(root.getString("Title"));
            return movie;
        } catch (JSONException e) {
            throw new ConnectException(e.getMessage());
        }

    }
}
