package com.github.super8.apis.tmdb.v3.parsers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.super8.model.Appearance;
import com.github.super8.model.CastAppearance;
import com.github.super8.model.Credits;
import com.github.super8.model.CrewAppearance;

public class TmdbCreditsParser extends TmdbParser<Credits> {

  @Override
  public Credits parseModel(JSONObject modelObject, Credits credits) throws JSONException {
    if (credits == null) {
      credits = new Credits();
    }
    if (modelObject.has("cast")) {
      credits.setCastAppearances(parseCastAppearances(modelObject));
    }
    if (modelObject.has("crew")) {
      credits.setCrewAppearances(parseCrewAppearances(modelObject));
    }
    return credits;
  }

  private ArrayList<CastAppearance> parseCastAppearances(JSONObject modelObject)
      throws JSONException {
    JSONArray castArray = modelObject.getJSONArray("cast");
    ArrayList<CastAppearance> appearances = new ArrayList<CastAppearance>(castArray.length());
    for (int i = 0; i < castArray.length(); i++) {
      JSONObject castObject = castArray.getJSONObject(i);
      CastAppearance appearance = new CastAppearance();
      parseAppearanceFields(castObject, appearance);
      appearance.setCharacter(castObject.getString("character"));
      appearances.add(appearance);
    }
    return appearances;
  }

  private ArrayList<CrewAppearance> parseCrewAppearances(JSONObject modelObject)
      throws JSONException {
    JSONArray crewArray = modelObject.getJSONArray("crew");
    ArrayList<CrewAppearance> appearances = new ArrayList<CrewAppearance>(crewArray.length());
    for (int i = 0; i < crewArray.length(); i++) {
      JSONObject castObject = crewArray.getJSONObject(i);
      CrewAppearance appearance = new CrewAppearance();
      parseAppearanceFields(castObject, appearance);
      appearance.setDepartment(castObject.getString("department"));
      appearance.setJob(castObject.getString("job"));
      appearances.add(appearance);
    }
    return appearances;
  }

  private void parseAppearanceFields(JSONObject jsonObject, Appearance appearance)
      throws JSONException {
    appearance.setTmdbId(jsonObject.getInt("id"));
    appearance.setMovieTitle(jsonObject.getString("title"));
    appearance.setMoviePosterPath(jsonObject.getString("poster_path"));
    appearance.setMovieReleaseDate(parseDate(jsonObject.getString("release_date")));
  }
}
