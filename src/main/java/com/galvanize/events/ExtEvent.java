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

    public ExtEvent(Long id, String creatorID, String organization, String name, String type, String description, Double baseCost, String status, Boolean isPublic) {
        this.id = id;
        this.creatorID = creatorID;
        this.organization = organization;
        this.name = name;
        this.type = type;
        this.description = description;
        this.baseCost = baseCost;
        this.status = status;
        this.isPublic = isPublic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public HashMap<String, String> getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(HashMap<String, String> startLocation) {
        this.startLocation = startLocation;
    }

    public HashMap<String, String> getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(HashMap<String, String> endLocation) {
        this.endLocation = endLocation;
    }

    public Double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(Double baseCost) {
        this.baseCost = baseCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public String toString() {
        return this.name + " " + this.startLocation;
    }
}
