package com.github.super8.model;

import java.util.Date;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Person extends ImageRecord {

  public static final String EXTRA_KEY = "person";
  
  private String name, biography;
  private Date birthday;

  public void addTo(Bundle bundle) {
    bundle.putParcelable(EXTRA_KEY, this);
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  @Override
  public String toString() {
    return name;
  }

  public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
    @Override
    public Person createFromParcel(Parcel source) {
      Person person = new Person();
      person.readFromParcel(source);
      return person;
    }

    @Override
    public Person[] newArray(int size) {
      return new Person[size];
    }
  };

  @Override
  protected void readFromParcel(Parcel source) {
    super.readFromParcel(source);
    this.name = source.readString();
    this.biography = source.readString();
    long epoch = source.readLong();
    if (epoch > -1) {
      this.birthday = new Date(epoch);
    }
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeString(name);
    dest.writeString(biography);
    if (birthday != null) {
      dest.writeLong(birthday.getTime());
    } else {
      dest.writeLong(-1);
    }
  }

}
