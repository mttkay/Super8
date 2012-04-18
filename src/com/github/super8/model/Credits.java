package com.github.super8.model;

import java.util.ArrayList;
import java.util.List;

public class Credits extends TmdbRecord {

  private List<CastAppearance> castAppearances;
  private List<CrewAppearance> crewAppearances;

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
    int containerSize = 0;
    if (castAppearances != null) {
      containerSize += castAppearances.size();
    }
    if (crewAppearances != null) {
      containerSize += crewAppearances.size();
    }
    ArrayList<Appearance> all = new ArrayList<Appearance>(containerSize);
    if (castAppearances != null) {
      all.addAll(castAppearances);
    }
    if (crewAppearances != null) {
      all.addAll(crewAppearances);
    }

    return all;
  }
}
