package com.galvanize.events;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class EventsService {

    EventsRepository eventsRepository;

    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    public EventList getEvents() {
        return new EventList(eventsRepository.findAll());
    }

    public Event getEventById(Long id) {
        return eventsRepository.findById(id).orElseThrow(EventNotFoundException::new);
    }

    public Event addEvent(Event newEvent) {
        try{
            return eventsRepository.save(newEvent);
        } catch(IllegalArgumentException e) {
            throw new InvalidEventException();
        }
    }

    public void deleteEvent(Long id) {
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

    public Event updateEvent(Long id, String status) {
        Optional<Event> oEvent = eventsRepository.findById(id);
        if(oEvent.isPresent()){
            try {
                oEvent.get().setStatus(status);
                return eventsRepository.save(oEvent.get());
            } catch(IllegalArgumentException e) {
                throw new InvalidEventUpdateException();
            }
        }else{
            throw new EventNotFoundException();
        }
    }

    public Event updateEvent(Long id, Date startDateTime, Date endDateTime) {
        Optional<Event> oEvent = eventsRepository.findById(id);
        if(oEvent.isPresent()){
            try {
                oEvent.get().setStartDateTime(startDateTime);
                oEvent.get().setEndDateTime(endDateTime);
                return eventsRepository.save(oEvent.get());
            } catch(IllegalArgumentException e) {
                throw new InvalidEventUpdateException();
            }
        }else{
            throw new EventNotFoundException();
        }
    }

    public Event updateEventStart(Long id, Date startDateTime) {
        Optional<Event> oEvent = eventsRepository.findById(id);
        if(oEvent.isPresent()){
            try {
                oEvent.get().setStartDateTime(startDateTime);
                return eventsRepository.save(oEvent.get());
            } catch(IllegalArgumentException e) {
                throw new InvalidEventUpdateException();
            }
        }else{
            throw new EventNotFoundException();
        }
    }

    public Event updateEventEnd(Long id, Date endDateTime) {
        Optional<Event> oEvent = eventsRepository.findById(id);
        if(oEvent.isPresent()){
            try {
                oEvent.get().setEndDateTime(endDateTime);
                return eventsRepository.save(oEvent.get());
            } catch(IllegalArgumentException e) {
                throw new InvalidEventUpdateException();
            }
        }else{
            throw new EventNotFoundException();
        }
    }
}
