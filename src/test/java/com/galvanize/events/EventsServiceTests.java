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
import static org.mockito.ArgumentMatchers.*;
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
    void getPublicEvents() {
        //todo mock call to itinerary api to get dates
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
         Event event = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", true);
        when(eventsRepository.findByIsPublic(true)).thenReturn(Arrays.asList(event));
        EventList eventsList = eventsService.getEvents();
        assertThat(eventsList).isNotNull();
        assertThat(eventsList.isEmpty()).isFalse();
    }

//    @Test
//    void getEventsByAdmin() {
//        //todo mock call to itinerary api to get dates
//        HashMap<String, String> startAddress = new HashMap<>();
//        startAddress.put("name", "Tiki Bar");
//        startAddress.put("address", "555 Elm Street");
//        startAddress.put("city", "Anyplace");
//        startAddress.put("state", "GA");
//        startAddress.put("zipcode", "55555");
//        HashMap<String, String> endAddress = new HashMap<>();
//        endAddress.put("name", "Tavern");
//        endAddress.put("address", "555 Main Street");
//        endAddress.put("city", "Anyplace");
//        endAddress.put("state", "GA");
//        endAddress.put("zipcode", "55555");
//        Date startDate= new Date(2001, 01, 01, 10,00, 00);
//        Date endDate= new Date(2001, 01, 02, 04,00, 00);
//        Event event = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", false);
//        when(eventsRepository.findAll()).thenReturn(Arrays.asList(event));
//        EventList eventsList = eventsService.getEvents();
//        assertThat(eventsList).isNotNull();
//        assertThat(eventsList.isEmpty()).isFalse();
//    }

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
         Event event = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", false);
        when(eventsRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(event));

        Event foundEvent = eventsService.getEventById(anyLong());
        assertThat(foundEvent).isNotNull();
        assertThat(foundEvent.getId()).isEqualTo(event.getId());
        //todo add other values if desired
    }

    @Test
    public void getEventListByCreatorId() {
        //todo mock call to itinerary api to get dates
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
        Event event = new Event("Bob", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", false);
        when(eventsRepository.findByCreatorID(anyString()))
                .thenReturn(Arrays.asList(event));

        EventList foundEvents = eventsService.getEventByCreator("Bob");
        assertThat(foundEvents).isNotNull();
        assertThat(foundEvents.get(0).getCreatorID()).isEqualTo(event.getCreatorID());
        //todo add other values if desired
    }

    @Test
    public void getEventByCreatorIdNoContent() {
        when(eventsRepository.findByCreatorID(anyString()))
                .thenThrow(EventNotFoundException.class);
        assertThatExceptionOfType(EventNotFoundException.class).isThrownBy(() -> {
            eventsService.getEventByCreator("Bob");
        });
    }

    @Test
    public void getEventByIdNoContent() {
        when(eventsRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EventNotFoundException.class).isThrownBy(() -> {
            eventsService.getEventById(anyLong());
        });
    }

    @Test
    void addEvent() {
        Event newEvent = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", false);
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
    void AddEventBadRequest() {
        when(eventsRepository.save(ArgumentMatchers.any()))
                .thenThrow(IllegalArgumentException.class);
        assertThatExceptionOfType(InvalidEventException.class).isThrownBy(() ->{
            eventsService.addEvent(new Event());
        });
    }

    @Test
    void updateEventReturnsEvent() {
         Event event = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", false);
        when(eventsRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(event));
        when(eventsRepository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);
        Event updatedEvent = eventsService.updateEvent(10L, event);
        assertThat(event).isNotNull();
        assertThat(updatedEvent.getId()).isEqualTo(event.getId());
    }

    @Test
    public void updateEventByIDNotExists() {
        when(eventsRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EventNotFoundException.class).isThrownBy(() -> {
            eventsService.updateEvent(10L, new Event());
        });
    }

    @Test
    public void updateEventStatusReturnsUpdatedEvent(){
        Event event = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", false);
        when(eventsRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(event));
        when(eventsRepository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);

        Long id = event.getId();
        Event updatedEvent = eventsService.updateEvent(10L, "upcoming");
        assertThat(event).isNotNull();
        assertThat(updatedEvent.getId()).isEqualTo(event.getId());
        assertThat(updatedEvent.getStatus()).isEqualTo(event.getStatus());
    }

    @Test
    public void updateEventStatusReturnIDNotExists() {
        when(eventsRepository.findById((anyLong())))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EventNotFoundException.class).isThrownBy(() -> {
            eventsService.updateEvent(anyLong(),"badID");
        });
    }

    @Test
    public void updateEventStatusReturnsIllegalArgument() {
        Event event = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", false);
        when(eventsRepository.findById(anyLong()))
                .thenReturn(java.util.Optional.of(event));
        when(eventsRepository.save(ArgumentMatchers.any()))
                .thenThrow(IllegalArgumentException.class);
        assertThatExceptionOfType(InvalidEventUpdateException.class).isThrownBy(() ->{
            eventsService.updateEvent(anyLong(),"badID");
        });
    }

    @Test
    void deleteEvent_byID() {
       Event event = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", false);
        when(eventsRepository.findById(anyLong()))
               .thenReturn(java.util.Optional.of(event));

        eventsService.deleteEvent(10L);

        verify(eventsRepository).delete(ArgumentMatchers.any(Event.class));
    }

    @Test
    public void deleteEventByIDNotExists() {
        when(eventsRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThatExceptionOfType(EventNotFoundException.class).isThrownBy(() -> {
            eventsService.deleteEvent(anyLong());
        });
    }
}
