package com.galvanize.events;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExtEvent {

    private Long id;
    private String creatorID; //todo finalize format
    private String organization;
    private String name;
    private String type;
    private String description;

    private String startDateTime;

    private String endDateTime;
    private HashMap<String, String> startLocation;
    private HashMap<String, String> endLocation;
    private Double baseCost;
    private String status; //todo change to enum
    private Boolean isPublic;

    public ExtEvent(Long id, String creatorID, String organization, String name, String type, String description, String startDateTime, String endDateTime, HashMap<String, String> startLocation, HashMap<String, String> endLocation, Double baseCost, String status, Boolean isPublic) {
        this.id = id;
        this.creatorID = creatorID;
        this.organization = organization;
        this.name = name;
        this.type = type;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.baseCost = baseCost;
        this.status = status;
        this.isPublic = isPublic;
    }
}
