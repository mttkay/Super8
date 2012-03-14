package com.github.super8.apis.amazon;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.github.ignition.support.http.IgnitedHttp;
import com.github.ignition.support.http.IgnitedHttpResponse;
import com.github.super8.apis.ServerCommunicationException;
import com.github.super8.model.Movie;
import com.google.inject.Inject;

public class AmazonApi {

    private static final String AWS_ACCESS_KEY_ID = "AKIAJPGATKFEMCQ4FTHQ";
    private static final String AWS_SECRET_KEY = "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";
    private static final String ENDPOINT = "ecs.amazonaws.com";
    private static final String SERVICE = "AWSECommerceService";
    private static final String VERSION = "2010-11-01";
    private static final String INDEX = "DVD";

    private SignedRequestsHelper signer;

    private IgnitedHttp http;

    @Inject
    public AmazonApi(Context context, IgnitedHttp http) {
        try {
            this.http = http;
            this.signer = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID,
                    AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Movie> performItemSearch(String query) throws ServerCommunicationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", SERVICE);
        params.put("Version", VERSION);
        params.put("SearchIndex", INDEX);
        params.put("Operation", "ItemSearch");
        params.put("ResponseGroup", "Medium");
        params.put("Title", query);

        String url = signer.sign(params);

        System.out.println(url);

        try {
            IgnitedHttpResponse response = http.get(url).send();
            System.out.println(">>>> " + response.getStatusCode());
            // System.out.println(response.getResponseBodyAsString());
            AmazonMovieParser parser = new AmazonMovieParser();
            return parser.parseMovies(response);
        } catch (ConnectException e) {
            throw new ServerCommunicationException(e);
        }
    }
}
