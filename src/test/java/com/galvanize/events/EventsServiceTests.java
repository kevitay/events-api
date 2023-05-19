package com.galvanize.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventsServiceTests {

    private EventsService eventsService;

    @Mock
    EventsRepository eventsRepository;

    @BeforeEach
    void setup() {
        eventsService = new EventsService(eventsRepository);
    }

    @Test
    void getEvents() {
        HashMap<String, String> startAddress = new HashMap<>();
        startAddress.put("name", "Tiki Bar");
        startAddress.put("address", "555 Elm Street");
        startAddress.put("city", "Anyplace");
        startAddress.put("state", "GA");
        startAddress.put("zipcode", "55555");
        HashMap<String, String> endAddress = new HashMap<>();
        endAddress.put("name", "Tavern");
        endAddress.put("address", "555 Main Street");
        endAddress.put("city", "Anyplace");
        endAddress.put("state", "GA");
        endAddress.put("zipcode", "55555");
        Date startDate= new Date(2001, 01, 01, 10,00, 00);
        Date endDate= new Date(2001, 01, 02, 04,00, 00);
        Event event = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", startDate, endDate, startAddress, endAddress, "asdkfadsf", 50.01, 150.01, "planned", false);
        when(eventsRepository.findAll()).thenReturn(Arrays.asList(event));
        EventList eventsList = eventsService.getEvents();
        assertThat(eventsList).isNotNull();
        assertThat(eventsList.isEmpty()).isFalse();
    }

    @Test
    void addEvent() {
        HashMap<String, String> startAddress = new HashMap<>();
        startAddress.put("name", "Tiki Bar");
        startAddress.put("address", "555 Elm Street");
        startAddress.put("city", "Anyplace");
        startAddress.put("state", "GA");
        startAddress.put("zipcode", "55555");
        HashMap<String, String> endAddress = new HashMap<>();
        endAddress.put("name", "Tavern");
        endAddress.put("address", "555 Main Street");
        endAddress.put("city", "Anyplace");
        endAddress.put("state", "GA");
        endAddress.put("zipcode", "55555");
        Date startDate= new Date(2001, 01, 01, 10,00, 00);
        Date endDate= new Date(2001, 01, 02, 04,00, 00);
        Event newEvent = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", startDate, endDate, startAddress, endAddress, "asdkfadsf", 50.01, 150.01, "planned", false);
        when(eventsRepository.save(ArgumentMatchers.any(Event.class)))
                .thenReturn(newEvent);
        Event event = eventsService.addEvent(newEvent);
        assertThat(event).isNotNull();
        assertThat(event.getOrganization()).isEqualTo("Phils Buds");
        assertThat(event.getName()).isEqualTo("St. Patricks Bar Crawl");
        assertThat(event.getDescription()).isEqualTo("21st Birthday Pub Crawl");
        //todo validate other fields
    }

    @Test
    void deleteEvent_byID() {
        Event event = new Event("aabbcc1234", "Phils Buds", "St. Patricks Bar Crawl '01", "Social", "Phil's 21st Birthday Pub Crawl", "2001-01-01T16:00-04:00", "2001-01-02T02:00-04:00", "Phil's Tiki Bar", "123 Example St", "Normal", "IL", 61761, "Greg's Oldtowne Tavern", "123 Example St", "Normal", "IL",61761, "1", 50, 50, false;);
        //String jsonEvent = "{\"creatorId\": \"aabbcc1234\",\"organization\": \"Phils Buds\",\"name\": \"St. Patricks Bar Crawl '01\",\"type\": \"Social\",\"description\": \"Phil's 21st Birthday Pub Crawl\",\"startDateTime\": \"2001-01-01T16:00-04:00\",\"endDateTime\": \"2001-01-02T02:00-04:00\",\"startLocation\": {\"name\": \"Phil's Tiki Bar\",\"address\": \"123 Example St\",\"city\": \"Normal\",\"state\": \"IL\",\"zipCode\": 61761},\"endLocation\": {\"name\": \"Greg's Oldtowne Tavern\",\"address\": \"123 Example St\",\"city\": \"Normal\",\"state\": \"IL\",\"zipCode\": 61761},\"participantListId\": \"1\",\"base_cost\": \"50\",\"total_cost\": \"50\",\"status\": \"planned\",\"isPublic\": false}";
        //event.setColor("RED");
        when(eventsRepository.findById(UUID.fromString(anyString())))
                .thenReturn(java.util.Optional.of(event));

        eventsService.deleteEvent(event.getId());

        verify(eventsRepository).delete(ArgumentMatchers.any(Event.class));
    }
}
