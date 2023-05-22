package com.galvanize.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
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
    public void getEventById() {
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
        when(eventsRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(java.util.Optional.of(event));

        Event foundEvent = eventsService.getEventById(UUID.randomUUID());
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getId()).isEqualTo(event.getId());
        //todo add other values if desired
    }

    @Test
    public void getEventByIdNoContent() {
        when(eventsRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EventNotFoundException.class).isThrownBy(() -> {
            eventsService.getEventById(UUID.randomUUID());
        });
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
    void updateEventReturnsEvent() {
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
        when(eventsRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(java.util.Optional.of(event));
        when(eventsRepository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        Event updatedEvent = eventsService.updateEvent(event);
        assertThat(event).isNotNull();
        assertThat(updatedEvent.getId()).isEqualTo(event.getId());
    }

    @Test
    public void updateEventByIDNotExists() {
        when(eventsRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EventNotFoundException.class).isThrownBy(() -> {
            eventsService.updateEvent(new Event());
        });
    }

        @Test
        void deleteEvent_byID() {
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
        when(eventsRepository.findById(ArgumentMatchers.any(UUID.class)))
               .thenReturn(java.util.Optional.of(event));

        eventsService.deleteEvent(event.getId());

        verify(eventsRepository).delete(ArgumentMatchers.any(Event.class));
    }

    @Test
    public void deleteEventByIDNotExists() {
        when(eventsRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EventNotFoundException.class).isThrownBy(() -> {
            eventsService.deleteEvent(UUID.randomUUID());
        });
    }

}
