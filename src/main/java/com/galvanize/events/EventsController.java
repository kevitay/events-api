package com.galvanize.events;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
    @RequestMapping("/api/event")
public class EventsController {

    private List<Event> eventList = new ArrayList<>();

    @GetMapping
    public List<Event> getEventList() {return eventList; }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event newEvent){
        eventList.add(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }
}
