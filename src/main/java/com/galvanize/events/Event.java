package com.galvanize.events;
import javax.persistence.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String creatorID; //todo finalize format
    private String organization;
    @Column(name = "event_name")
    private String name;
    private String type;
    private String description;
    private Double baseCost;
    private String status; //todo change to enum
    private Boolean isPublic;

    public Event() {
    }

    public Event(String creatorID, String organization, String name, String type, String description, Double baseCost, String status, Boolean isPublic) {
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

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}
