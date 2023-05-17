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
}
