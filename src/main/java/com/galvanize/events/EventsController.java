package com.galvanize.events;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    public EventList getEventList() {
        EventList eventList;
        eventList = eventsService.getEvents();
        return eventList;
    }
    @ResponseBody
    @GetMapping("/extended")
    public ExtEventList getExtEventList() {
        //first get list of events and break down to just their event IDs
        EventList eventList;
        eventList = eventsService.getEvents();
        ArrayList<Long> eventIds = new ArrayList<>();
        for (int i = 0; i < eventList.size(); i++) {
            eventIds.add(eventList.get(i).getId());
        }
        EventIdArray eventIdArray = new EventIdArray(eventIds);
        System.out.println(eventIdArray);

        //then send that list to itinerary API to get dates and locations
        ExtEventList extEventList = new ExtEventList();
        if(!eventList.isEmpty()) {
            String url = "http://a08cb134e19c8438285f05f4a630b6bd-117037464.us-west-2.elb.amazonaws.com/api/activities/summaryList";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<EventIdArray> httpEntity = new HttpEntity<>(eventIdArray, headers);
            RestTemplate restTemplate = new RestTemplate();
            System.out.println(eventIdArray);
            ResponseEntity<EventSummaryList> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, EventSummaryList.class);
            //finally merge those with the original event details to return

//      Iterate over each entry in the eventSummaryList returned from itinerary
            for (int i = 0; i < response.getBody().size(); i++) {
//          Iterate over original event list to locate matching event for the current eventSummary
                for (int j = 0; j < eventList.size(); j++) {
                    if (eventList.get(j).getId() == response.getBody().get(i).getEventId()) {
//                  Building starting and ending Hashmap to convert from Activity to desired format
                        HashMap<String, String> startAddress = new HashMap<>();
                        startAddress.put("address", response.getBody().get(i).getStartingActivity().getAddress());
                        startAddress.put("city", response.getBody().get(i).getStartingActivity().getCity());
                        startAddress.put("state", response.getBody().get(i).getStartingActivity().getState());
                        startAddress.put("zipcode", response.getBody().get(i).getStartingActivity().getZipToString());
                        HashMap<String, String> endAddress = new HashMap<>();
                        endAddress.put("address", response.getBody().get(i).getEndingActivity().getAddress());
                        endAddress.put("city", response.getBody().get(i).getEndingActivity().getCity());
                        endAddress.put("state", response.getBody().get(i).getEndingActivity().getState());
                        endAddress.put("zipcode", response.getBody().get(i).getEndingActivity().getZipToString());
//                  Create instance of extEvent to start building our response body
                        ExtEvent extEvent = new ExtEvent(eventList.get(j).getId(),
                                eventList.get(j).getCreatorID(),
                                eventList.get(j).getOrganization(),
                                eventList.get(j).getName(),
                                eventList.get(j).getType(),
                                eventList.get(j).getDescription(),
                                response.getBody().get(i).getStartingActivity().getStartTime(),
                                response.getBody().get(i).getEndingActivity().getEndTime(),
                                startAddress,
                                endAddress,
                                eventList.get(j).getBaseCost(),
                                eventList.get(j).getStatus(),
                                eventList.get(j).getPublic());
                        extEventList.add(extEvent);
                    }
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
