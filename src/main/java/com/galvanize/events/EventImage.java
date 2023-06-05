package com.galvanize.events;

import javax.persistence.*;

@Entity
@Table(name = "event_image")
public class EventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Byte[] data;
    private Long eventId;

    public EventImage(){
    }

    public EventImage(Long eventId){
        this.eventId = eventId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte[] getData() {
        return data;
    }

    public void setData(Byte[] data) {
        this.data = data;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
