package com.galvanize.events;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event newEvent){
        eventsService.addEvent(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteEvent(@PathVariable UUID id) {
         eventsService.deleteEvent(id);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable UUID id, @RequestBody Event event) {
        return eventsService.updateEvent(event);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void noAutoFound(EventNotFoundException e) {
    }
}
