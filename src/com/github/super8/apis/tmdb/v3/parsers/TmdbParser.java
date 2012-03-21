package com.github.super8.apis.tmdb.v3.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.github.super8.apis.tmdb.v3.TmdbApi;
import com.github.super8.model.TmdbRecord;


public abstract class TmdbParser<ModelT extends TmdbRecord> {

  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public List<ModelT> parseList(String json) throws JSONException {
    JSONObject responseObject = new JSONObject(json);
    JSONArray personArray = responseObject.getJSONArray("results");
    ArrayList<ModelT> modelList = new ArrayList<ModelT>(TmdbApi.DEFAULT_PAGE_SIZE);
    for (int i = 0; i < personArray.length(); i++) {
      modelList.add(parseOne(personArray.getJSONObject(i)));
    }
    return modelList;
  }

  public ModelT parseOne(String json) throws JSONException {
    return parseOne(new JSONObject(json));
  }

  private ModelT parseOne(JSONObject modelObject) throws JSONException {
    ModelT model = parseModel(modelObject);
    model.setTmdbId(modelObject.getInt("id"));
    return model;
  }
  
  public abstract ModelT parseModel(JSONObject modelObject) throws JSONException;

  protected Date parseDate(String date) {
    if (TextUtils.isEmpty(date)) {
      return null;
    }
    try {
      return DATE_FORMAT.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}
