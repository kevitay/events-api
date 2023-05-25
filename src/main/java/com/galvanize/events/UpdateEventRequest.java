package com.galvanize.events;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UpdateEventRequest {
  @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
  private Date startDateTime;
  @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
  private Date endDateTime;
  private String status;
//  private String creatorID; //todo finalize format

  public String getStatus() {
    return status;
  }

  public Date getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(Date startDateTime) {
    this.startDateTime = startDateTime;
  }

  public Date getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(Date endDateTime) {
    this.endDateTime = endDateTime;
  }

  public void setStatus(String status) {
    this.status = status;
  }

//  public String getCreatorID() {
//    return creatorID;
//  }
//
//  public void setCreatorID(String creatorID) {
//    this.creatorID = creatorID;
//  }
}
