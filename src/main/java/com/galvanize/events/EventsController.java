package com.galvanize.events;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/event")
public class EventsController {

    EventsService eventsService;

    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    public ExtEventList getEventList() {
        //first get list of event IDs
        EventList eventList;
        eventList = eventsService.getEvents();
        List<Long> eventIds = eventList.getIDList();

        //then send that list to itinerary API to get dates and locations

        String url = "http://ad0bcd07c990f4a9d9879e71472608fa-1526526031.us-west-2.elb.amazonaws.com/api/activities/getsummary";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<List<Long>> httpEntity = new HttpEntity<>(eventIds, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<EventSummaryList> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, EventSummaryList.class);
        //todo change object to what is provided from itinerary team
        //finally merge those with the original event details to return
        ExtEventList extEventList = new extEventList();
        for (int i =0; i < response.getBody().size(); i++) {
            for (int j = 0; j < eventList.size(); j++) {
                if(eventList.get(j).getId() == response.getBody().get(i).getId()) {
                    extEventList.add(eventList.get(j), response.getBody().get(i));
                    //todo can set specific fields from response.getbody() if needed.
                }
            }
        }
        return extEventList;
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
