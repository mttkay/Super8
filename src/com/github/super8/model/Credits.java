package com.github.super8.model;

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

}
