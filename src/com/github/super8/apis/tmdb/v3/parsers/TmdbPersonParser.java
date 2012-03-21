package com.github.super8.apis.tmdb.v3.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.super8.model.Person;

public class TmdbPersonParser extends TmdbParser<Person> {

  @Override
  public Person parseModel(JSONObject modelObject) throws JSONException {
    Person person = new Person();
    person.setName(modelObject.getString("name"));

    String imagePath = modelObject.getString("profile_path");
    if (!"null".equals(imagePath)) {
      person.setImagePath(imagePath);
    }

    // these fields are only available for a full person request
    person.setBirthday(parseDate(modelObject.optString("birthdate")));
    person.setBiography(modelObject.optString("biography"));

    return person;
  }

}
