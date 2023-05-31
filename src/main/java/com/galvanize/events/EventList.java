package com.galvanize.events;

import java.util.ArrayList;
import java.util.List;

public class EventList  {

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


    public int size() {
        if(this.eventList == null) {
            throw new NullPointerException("List is null");
        }
        int size = 0;
        for(Event event: eventList) {
            size++;
        }
        return size;
    }

    public boolean isEmpty() {
        return this.eventList.isEmpty();
    }


    public boolean contains(Object e) {
        if(this.eventList == null) {
            throw new NullPointerException("List is null");
        }
        for(Event event: eventList) {
            if (event.equals(e)) {
                return true;
            }
        }
             return false;
    }

    public Event get(int i) {
        return this.eventList.get(i);
    }

    public List<Long> getIDList() {
        if(this.eventList == null) {
            throw new NullPointerException("List is null");
        }
        List<Long> eventIds = new ArrayList<>();
        for(Event event: eventList) {
            eventIds.add(event.getId());
        }
        return eventIds;
    }
}
