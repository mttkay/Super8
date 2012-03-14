package com.github.super8.apis.amazon;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.github.super8.apis.AbstractXmlMovieParser;
import com.github.super8.model.Movie;

public class AmazonMovieParser extends AbstractXmlMovieParser {

    private static final String TAG = AmazonMovieParser.class.getSimpleName();

    @Override
    protected Movie parseMovie() throws XmlPullParserException, IOException, ParseException {
        Movie movie = new Movie();

        skipToTag("ItemAttributes");

        skipToTag("Title");
        movie.setTitle(xpp.nextText());

        skipToTag("ReleaseDate");
        Date releaseDate = releaseDateFormat.parse(xpp.nextText());
        movie.setReleaseDate(releaseDate);

        return movie;
    }
    
    @Override
    protected boolean skipToMovieItemTag() throws Exception {
        return skipToTag("Item");
    }

    @Override
    protected void validateResponse() throws XmlPullParserException, IOException {
        skipToTag("IsValid");
        boolean valid = Boolean.parseBoolean(xpp.nextText());
        if (!valid) {
            parseErrorMessage();
            throw new RuntimeException("Bad Amazon API request");
        }
    }

    private void parseErrorMessage() throws XmlPullParserException, IOException {
        skipToTag("Errors");
        while (skipToTag("Message")) {
            Log.e(TAG, xpp.nextText());
        }
    }

}
