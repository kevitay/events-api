package com.galvanize.events;

public class EventSummary {
    private Activity startingActivity;
    private Activity endingActivity;
    private Long eventId;

    public Activity getStartingActivity() {
        return startingActivity;
    }

    public void setStartingActivity(Activity startingActivity) {
        this.startingActivity = startingActivity;
    }

    public Activity getEndingActivity() {
        return endingActivity;
    }

    public void setEndingActivity(Activity endingActivity) {
        this.endingActivity = endingActivity;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

}
