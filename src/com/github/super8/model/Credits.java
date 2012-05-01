package com.github.super8.model;

import java.util.ArrayList;
import java.util.List;

public class Credits extends TmdbRecord {

  private List<CastAppearance> castAppearances;
  private List<CrewAppearance> crewAppearances;
  private int size = -1;

  public List<CastAppearance> getCastAppearances() {
    return castAppearances;
  }

  public void setCastAppearances(List<CastAppearance> castAppearances) {
    this.castAppearances = castAppearances;
  }

  public List<CrewAppearance> getCrewAppearances() {
    return crewAppearances;
  }

  public void setCrewAppearances(List<CrewAppearance> crewAppearances) {
    this.crewAppearances = crewAppearances;
  }

  public List<Appearance> getAllAppearances() {
    ArrayList<Appearance> all = new ArrayList<Appearance>(size());
    if (castAppearances != null) {
      all.addAll(castAppearances);
    }
    if (crewAppearances != null) {
      all.addAll(crewAppearances);
    }

    return all;
  }

  public int size() {
    if (size > -1) {
      return size;
    } else {
      size = 0;
    }
    if (castAppearances != null) {
      size += castAppearances.size();
    }
    if (crewAppearances != null) {
      size += crewAppearances.size();
    }
    return size;
  }
}
