package com.github.super8.apis.tmdb.v2;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import com.github.ignition.support.http.IgnitedHttp;
import com.github.ignition.support.http.IgnitedHttpResponse;
import com.github.super8.apis.ServerCommunicationException;
import com.github.super8.model.Movie;

public class TmdbApi {

    private static final String API_KEY = "bace5d090265e765e78bf188f414783e";
    private static final String ENDPOINT = "http://api.themoviedb.org/2.1";
    private static final String FORMAT = "xml";

    private IgnitedHttp http;

    public TmdbApi(IgnitedHttp http) {
        this.http = http;
    }

    public List<Movie> search(String query) throws ServerCommunicationException {

        String url = tmdbUrl("Movie.search", query);

        System.out.println(url);

        try {
            IgnitedHttpResponse response = http.get(url).send();
            System.out.println(">>>> " + response.getStatusCode());
            // System.out.println(response.getResponseBodyAsString());
            TmdbV2Parser parser = new TmdbV2Parser();
            return parser.parseMovies(response);
        } catch (ConnectException e) {
            throw new ServerCommunicationException(e);
        }
    }

    private String tmdbUrl(String action, String query) {
        try {
            return ENDPOINT + "/" + action + "/" + Locale.getDefault().getLanguage() + "/" + FORMAT
                    + "/" + API_KEY + "/" + URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
