package com.galvanize.events;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@RequestMapping("/api/event")
public class EventsController {

    EventsService eventsService;

    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    public EventList getEventList() {
        EventList eventList;
        eventList = eventsService.getEvents();
        return eventList;
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventsService.getEventById(id);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event newEvent){
        eventsService.addEvent(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteEvent(@PathVariable Long id) {
         eventsService.deleteEvent(id);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventsService.updateEvent(id, event);
    }

    @PatchMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody UpdateEventRequest update) {
        if(!(update.getStatus() == null)) {
            return eventsService.updateEvent(id, update.getStatus());
        } else if (!(update.getStartDateTime() == null) && !(update.getEndDateTime() == null)) {
            return eventsService.updateEvent(id, update.getStartDateTime(), update.getEndDateTime());
        }  else if (!(update.getStartDateTime() == null)) {
            return eventsService.updateEventStart(id, update.getStartDateTime());
        }  else if (!(update.getEndDateTime() == null)) {
            return eventsService.updateEventEnd(id, update.getEndDateTime());
        }
        return new Event(); // todo change to optional?
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void noEventFound(EventNotFoundException e) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidEventFound(InvalidEventException e) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidEventUpdate(InvalidEventUpdateException w) {
    }
}
