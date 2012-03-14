package com.github.super8.apis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.text.TextUtils;

import com.github.ignition.support.http.IgnitedHttpResponse;
import com.github.super8.model.Movie;

public abstract class AbstractXmlMovieParser {

  protected SimpleDateFormat releaseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  protected SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

  protected XmlPullParser xpp;

  public AbstractXmlMovieParser() {
    XmlPullParserFactory factory;
    try {
      factory = XmlPullParserFactory.newInstance();
      this.xpp = factory.newPullParser();
    } catch (XmlPullParserException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public List<Movie> parseMovies(IgnitedHttpResponse response) throws ServerCommunicationException {
    try {
      List<Movie> movies = new LinkedList<Movie>();
      xpp.setInput(new BufferedReader(new InputStreamReader(response.getResponseBody())));

      validateResponse();

      while (skipToMovieItemTag()) {
        Movie movie = parseMovie();
        movies.add(movie);
      }

      return movies;
    } catch (Exception e) {
      String lastElement = xpp.getName();
      String lastText = xpp.getText();
      StringBuilder sb = new StringBuilder();
      if (lastElement != null) {
        sb.append("Failed parsing XML at element '" + lastElement + "'");
      }
      if (lastText != null) {
        sb.append(": " + lastText);
      }
      throw new ServerCommunicationException(sb.toString(), e);
    }
  }

  protected abstract boolean skipToMovieItemTag() throws Exception;

  protected abstract Movie parseMovie() throws Exception;

  protected void validateResponse() throws Exception {
  }

  protected boolean skipToTag(String tagName, String... attrValuePairs)
      throws XmlPullParserException, IOException {
    int event = xpp.getEventType();
    while (event != XmlPullParser.END_DOCUMENT) {
      if (event == XmlPullParser.START_TAG && tagName.equals(xpp.getName())) {
        if (attrValuePairs.length == 0) {
          return true;
        } else if (xpp.getAttributeCount() > 0 && attributesMatch(attrValuePairs)) {
          return true;
        }
      }
      event = xpp.next();
    }
    return false;
  }
  
  protected String nextText() throws XmlPullParserException, IOException {
    String nextText = xpp.nextText();
    if (TextUtils.isEmpty(nextText)) {
      return null;
    }
    return nextText;
  }

  private boolean attributesMatch(String[] attrValuePairs) {
    int numAttrs = xpp.getAttributeCount();
    for (int i = 0; i < attrValuePairs.length / 2; i++) {
      String attribute = attrValuePairs[0];
      String value = attrValuePairs[1];
      for (int j = 0; j < numAttrs; j++) {
        if (attribute.equals(xpp.getAttributeName(j)) && value.equals(xpp.getAttributeValue(j))) {
          return true;
        }
      }
    }
    return false;
  }
}
