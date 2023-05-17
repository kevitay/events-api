package com.galvanize.events;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
    @RequestMapping("/api/event")
public class EventsController {

    private List<Event> eventList = new ArrayList<>();

    @GetMapping
    public List<Event> getEventList() {return eventList; }


}
