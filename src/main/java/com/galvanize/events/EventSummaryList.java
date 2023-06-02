package com.galvanize.events;

import java.util.ArrayList;
import java.util.List;

public class EventSummaryList {
    List<EventSummary> eventSummaryList;

    public EventSummaryList() {
        this.eventSummaryList = new ArrayList<>();
    }
    public EventSummaryList(List<EventSummary> eventSummaryList) {
        this.eventSummaryList = eventSummaryList;
    }

    public List<EventSummary> getEventSummaryList() {
        return eventSummaryList;
    }

    public void setEventSummaryList(List<EventSummary> eventSummaryList) {
        this.eventSummaryList = eventSummaryList;
    }

    public void add(EventSummary eventSummary) {
        eventSummaryList.add(eventSummary);
    }

    public boolean isEmpty() {
        return eventSummaryList.isEmpty();
    }

    public EventSummary get(int id) {
        return eventSummaryList.get(id);
    }

    public int size() {
        return eventSummaryList.size();
    }

    @Override
    public String toString() {
        return eventSummaryList.get(0).getStartingActivity().getAddress();
    }
}
