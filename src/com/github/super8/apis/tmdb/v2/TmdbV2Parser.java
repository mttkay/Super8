package com.github.super8.apis.tmdb.v2;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserException;

import com.github.super8.apis.AbstractXmlMovieParser;
import com.github.super8.model.Movie;

public class TmdbV2Parser extends AbstractXmlMovieParser {

  @Override
  protected Movie parseMovie() throws XmlPullParserException, IOException, ParseException {

    Movie movie = new Movie();

    skipToTag("original_name");
    movie.setTitle(xpp.nextText());
    System.out.println("Parsing " + movie.getTitle());

    skipToTag("released");
    String dateText = nextText();
    if (dateText != null) {
      Date releaseDate = releaseDateFormat.parse(dateText);
      movie.setReleaseDate(releaseDate);
    }

//    skipToTag("image", "size", "mid");
//    movie.setPosterImageUrl(xpp.getAttributeValue(null, "url"));

    return movie;
  }

  @Override
  protected boolean skipToMovieItemTag() throws Exception {
    return skipToTag("movie");
  }

}
