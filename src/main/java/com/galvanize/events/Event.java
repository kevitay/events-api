package com.galvanize.events;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Event {

    private String creatorID; //todo finalize format
    private String organization;
    private String name;
    private String type;
    private String description;

    private Date startDateTime;
    private Date endDateTime;
    private Address startLocation;
    private Address endLocation;
    private String participantListId; //todo get data type
    private Double baseCost;
    private Double totalCost;
    private String status; //todo change to enum
    private Boolean isPublic;

    public Event() {
    }

    public Event(String creatorID, String organization, String name, String type, String description, Date startDateTime, Date endDateTime, Address startLocation, Address endLocation, String participantListId, Double baseCost, Double totalCost, String status, Boolean isPublic) {
        this.creatorID = creatorID;
        this.organization = organization;
        this.name = name;
        this.type = type;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.participantListId = participantListId;
        this.baseCost = baseCost;
        this.totalCost = totalCost;
        this.status = status;
        this.isPublic = isPublic;
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

    public Address getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Address startLocation) {
        this.startLocation = startLocation;
    }

    public Address getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Address endLocation) {
        this.endLocation = endLocation;
    }

    public String getParticipantListId() {
        return participantListId;
    }

    public void setParticipantListId(String participantListId) {
        this.participantListId = participantListId;
    }

    public Double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(Double baseCost) {
        this.baseCost = baseCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
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
}
