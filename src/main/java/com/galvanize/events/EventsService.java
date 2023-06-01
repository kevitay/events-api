package com.galvanize.events;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Event updateEvent(Long id, Event updatedEvent) {
         Optional<Event> oEvent = eventsRepository.findById(id);
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

    public EventList getEventByCreator(String creatorId) {
        List<Event> eventList = eventsRepository.findByCreatorId(creatorId);
        if(!eventList.isEmpty()) {
            return new EventList(eventList);
        }
        return null;
    }
}
