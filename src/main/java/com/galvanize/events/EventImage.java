package com.galvanize.events;

import javax.persistence.*;

@Entity
@Table(name = "event_image")
public class EventImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "bytea")
    private byte[] data;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    Event event;

    public EventImage() {
    }

    public EventImage(Event event) {
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

}
