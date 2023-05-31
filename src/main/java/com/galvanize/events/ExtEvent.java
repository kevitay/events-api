package com.galvanize.events;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExtEvent {
    private List<ExtEvent> extEventListList;

    private Long id;
    private String creatorID; //todo finalize format
    private String organization;

    private String name;
    private String type;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private Date startDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    private Date endDateTime;

    private HashMap<String, String> startLocation;

    private HashMap<String, String> endLocation;
    private Double baseCost;
    private String status; //todo change to enum
    private Boolean isPublic;

}
