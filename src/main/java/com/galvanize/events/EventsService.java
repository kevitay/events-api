package com.galvanize.events;

import org.springframework.stereotype.Service;

@Service
public class EventsService {

    EventsRepository eventsRepository;

    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }


    public EventList getEvents() {
        return new EventList(eventsRepository.findAll());
    }

    public Event addEvent(Event newEvent) {
        try{
            return eventsRepository.save(newEvent);
        } catch(IllegalArgumentException e) {
            throw new InvalidEventException();
        }
    }
}
