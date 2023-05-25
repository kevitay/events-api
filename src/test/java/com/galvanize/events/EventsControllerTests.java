package com.galvanize.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.HashMap;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EventsController.class)
public class EventsControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventsService eventsService;
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void initialGetNoEvents() throws Exception {
        when(eventsService.getEvents()).thenReturn(new EventList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/event"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.eventList").isEmpty());
    }

    @Test
    public void getEventByIdReturnsEvent() throws Exception {
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

        Date startDate = new Date(2001, 01, 01, 10, 00, 00);
        Date endDate = new Date(2001, 01, 02, 04, 00, 00);
        Event existingEvent = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", startDate, endDate, startAddress, endAddress, "asdkfadsf", 50.01, 150.01, "planned", false);
        when(eventsService.getEventById(anyInt())).thenReturn(existingEvent);
        UUID id = existingEvent.getId();

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/event/%s", id)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.organization").value("Phils Buds"))
                .andExpect(jsonPath("$.name").value("St. Patricks Bar Crawl"))
                .andExpect(jsonPath("$.description").value("21st Birthday Pub Crawl"));
    }

    @Test
    public void getEventByIdReturnsNoContent() throws Exception {
        doThrow(new EventNotFoundException()).when(eventsService).getEventById(ArgumentMatchers.anyInt());
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/event/%s", UUID.randomUUID())))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void getEVentByIdBadFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(("/api/event/AABBCC")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void postEventReturnsEvent() throws Exception {
        //String jsonEvent = "{\"creatorId\": \"aabbcc1234\",\"organization\": \"Phils Buds\",\"name\": \"St. Patricks Bar Crawl '01\",\"type\": \"Social\",\"description\": \"Phil's 21st Birthday Pub Crawl\",\"startDateTime\": \"2001-01-01T16:00-04:00\",\"endDateTime\": \"2001-01-02T02:00-04:00\",\"startLocation\": {\"name\": \"Phil's Tiki Bar\",\"address\": \"123 Example St\",\"city\": \"Normal\",\"state\": \"IL\",\"zipCode\": 61761},\"endLocation\": {\"name\": \"Greg's Oldtowne Tavern\",\"address\": \"123 Example St\",\"city\": \"Normal\",\"state\": \"IL\",\"zipCode\": 61761},\"participantListId\": \"1\",\"base_cost\": \"50\",\"total_cost\": \"50\",\"status\": \"planned\",\"isPublic\": false}";
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

        Date startDate = new Date(2001, 01, 01, 10, 00, 00);
        Date endDate = new Date(2001, 01, 02, 04, 00, 00);
        Event newEvent = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", startDate, endDate, startAddress, endAddress, "asdkfadsf", 50.01, 150.01, "planned", false);
        when(eventsService.addEvent(any(Event.class))).thenReturn(newEvent);
        mockMvc.perform(post("/api/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newEvent)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.organization").value("Phils Buds"))
                .andExpect(jsonPath("$.name").value("St. Patricks Bar Crawl"))
                .andExpect(jsonPath("$.description").value("21st Birthday Pub Crawl"));
        //todo validate other fields in response
    }

    @Test
    public void postRequestReturnsBadRequest() throws Exception {
        when(eventsService.addEvent(any(Event.class))).thenThrow(InvalidEventException.class);
        mockMvc.perform(post("/api/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new Event())))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteEvent() throws Exception {
        mockMvc.perform(delete("/api/event/10"))
                .andExpect(status().isAccepted());
        verify(eventsService).deleteEvent(ArgumentMatchers.anyInt());
    }

    @Test
    public void deleteUnknownIdThrowsNoContent() throws Exception {
        doThrow(new EventNotFoundException()).when(eventsService).deleteEvent(ArgumentMatchers.anyInt());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/event/10"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void putEvent_returnsUpdatedEvent() throws Exception {
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

        Date startDate = new Date(2001, 01, 01, 10, 00, 00);
        Date endDate = new Date(2001, 01, 02, 04, 00, 00);
        Event newEvent = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", startDate, endDate, startAddress, endAddress, "asdkfadsf", 50.01, 150.01, "planned", false);
        when(eventsService.addEvent(any(Event.class))).thenReturn(newEvent);
        mockMvc.perform(post("/api/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newEvent)))
                .andExpect(status().isCreated());
        UUID id = newEvent.getId();
        newEvent.setOrganization("Bud's Buds");
        newEvent.setName("Different Name");
        newEvent.setDescription("New Birthday Bash");
        when(eventsService.updateEvent(any(Event.class))).thenReturn(newEvent);
        mockMvc.perform(MockMvcRequestBuilders.put(String.format("/api/event/%s", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newEvent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.organization").value("Bud's Buds"))
                .andExpect(jsonPath("$.name").value("Different Name"))
                .andExpect(jsonPath("$.description").value("New Birthday Bash"));
    }

    @Test
    public void updateThrowsBadRequest() throws Exception {
        doThrow(new InvalidEventException()).when(eventsService).updateEvent(ArgumentMatchers.any(Event.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new Event())))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateUnknownIdThrowsNoContent() throws Exception {
        doThrow(new EventNotFoundException()).when(eventsService).updateEvent(ArgumentMatchers.any(Event.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new Event())))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void patchEventStatusReturnsUpdatedEvent() throws Exception {
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

        Date startDate = new Date(2001, 01, 01, 10, 00, 00);
        Date endDate = new Date(2001, 01, 02, 04, 00, 00);
        Event updatedEvent = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", startDate, endDate, startAddress, endAddress, "asdkfadsf", 50.01, 150.01, "upcoming", false);

        UUID id = updatedEvent.getId();

        when(eventsService.updateEvent(anyInt(), ArgumentMatchers.anyString())).thenReturn(updatedEvent);
        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/api/event/%s", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"upcoming\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.status").value("upcoming"));
    }

    @Test
    public void updateStatusUnknownIdThrowsNoContent() throws Exception {
        doThrow(new EventNotFoundException()).when(eventsService).updateEvent(anyInt(), ArgumentMatchers.anyString());
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"upcoming\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void updateStatusThrowsInvalidUpdateException() throws Exception {
        doThrow(new InvalidEventUpdateException()).when(eventsService).updateEvent(anyInt(), ArgumentMatchers.anyString());
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"upcoming\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateDateFieldsReturnsEvent() throws Exception {
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

        Date startDate = new Date(2001, 01, 01, 06, 00, 00);
        Date endDate = new Date(2001, 01, 02, 10, 00, 00);
        Event updatedEvent = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", startDate, endDate, startAddress, endAddress, "asdkfadsf", 50.01, 150.01, "upcoming", false);
        UUID id = updatedEvent.getId();
        when(eventsService.updateEvent(anyInt(), any(Date.class), any(Date.class))).thenReturn(updatedEvent);
        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/api/event/%s", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDateTime\": \"3901-02-01@15:00:00\", \"endDateTime\": \"3901-02-02@09:00:00\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    public void updateStartDateFieldReturnsEvent() throws Exception {
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

        Date startDate = new Date(2001, 01, 01, 06, 00, 00);
        Date endDate = new Date(2001, 01, 02, 10, 00, 00);
        Event updatedEvent = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", startDate, endDate, startAddress, endAddress, "asdkfadsf", 50.01, 150.01, "upcoming", false);
        UUID id = updatedEvent.getId();
        when(eventsService.updateEventStart(anyInt(), any(Date.class))).thenReturn(updatedEvent);
        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/api/event/%s", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDateTime\": \"3901-02-01@15:00:00\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    public void updateEndDateFieldReturnsEvent() throws Exception {
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

        Date startDate= new Date(2001, 01, 01, 06,00, 00);
        Date endDate= new Date(2001, 01, 02, 10,00, 00);
        Event updatedEvent = new Event("AAADDD", "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", startDate, endDate, startAddress, endAddress, "asdkfadsf", 50.01, 150.01, "upcoming", false);
        UUID id = updatedEvent.getId();
        when(eventsService.updateEventEnd(anyInt(), any(Date.class))).thenReturn(updatedEvent);
        mockMvc.perform(MockMvcRequestBuilders.patch(String.format("/api/event/%s", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"endDateTime\": \"3901-02-02@09:00:00\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    public void updateDatesUnknownIdThrowsNoContent() throws Exception {
        doThrow(new EventNotFoundException()).when(eventsService).updateEvent(anyInt(), ArgumentMatchers.any(Date.class),ArgumentMatchers.any(Date.class));
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDateTime\": \"3901-02-01@15:00:00\", \"endDateTime\": \"3901-02-02@09:00:00\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void updateStartDateUnknownIdThrowsNoContent() throws Exception {
        doThrow(new EventNotFoundException()).when(eventsService).updateEventStart(anyInt(), ArgumentMatchers.any(Date.class));
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDateTime\": \"3901-02-01@15:00:00\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void updateEndDateUnknownIdThrowsNoContent() throws Exception {
        doThrow(new EventNotFoundException()).when(eventsService).updateEventEnd(anyInt(), ArgumentMatchers.any(Date.class));
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"endDateTime\": \"3901-02-02@09:00:00\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void updateDatesThrowsInvalidUpdateException() throws Exception {
        doThrow(new InvalidEventUpdateException()).when(eventsService).updateEvent(anyInt(), ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class));
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDateTime\": \"3901-02-01@15:00:00\", \"endDateTime\": \"3901-02-02@09:00:00\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateStartDateThrowsInvalidUpdateException() throws Exception {
        doThrow(new InvalidEventUpdateException()).when(eventsService).updateEventStart(anyInt(), ArgumentMatchers.any(Date.class));
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDateTime\": \"3901-02-01@15:00:00\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateEndDateThrowsInvalidUpdateException() throws Exception {
        doThrow(new InvalidEventUpdateException()).when(eventsService).updateEventEnd(anyInt(), ArgumentMatchers.any(Date.class));
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/event/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"endDateTime\": \"3901-02-02@09:00:00\"}"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}