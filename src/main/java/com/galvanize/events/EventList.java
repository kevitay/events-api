package com.galvanize.events;

import java.util.ArrayList;
import java.util.List;

public class EventList {

    private List<Event> eventList;

    public EventList(){
        this.eventList = new ArrayList<>();
    }

    public EventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public boolean isEmpty() {
        return this.eventList.isEmpty();
    }
}
