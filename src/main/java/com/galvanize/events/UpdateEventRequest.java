package com.galvanize.events;


public class UpdateEventRequest {

  private String status;
//  private String creatorID; //todo finalize format

  public String getStatus() {
    return status;
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
