package com.github.super8.model;

public class CrewAppearance extends Appearance {

  public static final String DEPARTMENT_DIRECTING = "Directing";
  public static final String DEPARTMENT_PRODUCTION = "Production";
  public static final String DEPARTMENT_WRITING = "Writing";
  public static final String DEPARTMENT_CAMERA = "Camera";
  public static final String JOB_DIRECTOR = "Director";
  public static final String JOB_PRODUCER = "Producer";
  public static final String JOB_EXECUTIVE_PRODUCER = "Executive Producer";
  public static final String JOB_AUTHOR = "Author";
  public static final String JOB_DOP = "Director of Photography";
  
  private String department, job;

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }
  
  
}
