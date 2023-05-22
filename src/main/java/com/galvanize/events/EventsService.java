package com.galvanize.events;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EventsService {

    EventsRepository eventsRepository;

    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }


    public EventList getEvents() {
        return new EventList(eventsRepository.findAll());
    }

    public Event getEventById(UUID id) {
        return eventsRepository.findById(id).orElseThrow(EventNotFoundException::new);
    }

    public Event addEvent(Event newEvent) {
        try{
            return eventsRepository.save(newEvent);
        } catch(IllegalArgumentException e) {
            throw new InvalidEventException();
        }
    }

    public void deleteEvent(UUID id) {
      Optional<Event> oEvent = eventsRepository.findById(id);
      if(oEvent.isPresent()){
          eventsRepository.delete(oEvent.get());
      }else{
          throw new EventNotFoundException();
      }
    }


    public Event updateEvent(Event updatedEvent) {
         Optional<Event> oEvent = eventsRepository.findById(updatedEvent.getId());
        if(oEvent.isPresent()){
            return eventsRepository.save(updatedEvent);
        }else{
            throw new EventNotFoundException();
        }
    }
}
