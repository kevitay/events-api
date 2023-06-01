package com.galvanize.events;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Activity {

    private Long id;
    private Long eventId;
    private Long parentId;
    private String activityName;
    private String startTime;
    private String endTime;
    private int groupSize;
    private boolean mandatory;
    private double price;
    private String type;
    private String description;
    private boolean indoor;
    private String url;
    private String imageURL;
    private String importantReminder;
    private String address;
    private String city;
    private String state;
    private int zip;

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZip() {
        return zip;
    }

    public String getZipToString() {
         return String.valueOf(this.zip);
    }
}