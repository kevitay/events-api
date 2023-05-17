package com.galvanize.events;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @Test
    public void initialGetNoEvents() throws Exception{
       mockMvc.perform(MockMvcRequestBuilders.get("/api/event"))
               .andDo(print())
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void postEventReturnsEvent() throws Exception {
        String jsonEvent = "{\"creatorId\": \"aabbcc1234\",\"organization\": \"Phils Buds\",\"name\": \"St. Patricks Bar Crawl '01\",\"type\": \"Social\",\"description\": \"Phil's 21st Birthday Pub Crawl\",\"startDateTime\": \"2001-01-01T16:00-04:00\",\"endDateTime\": \"2001-01-02T02:00-04:00\",\"startLocation\": {\"startName\": \"Phil's Tiki Bar\",\"Address\": \"123 Example St\",\"City\": \"Normal\",\"State\": \"IL\",\"ZipCode\": 61761},\"endLocation\": {\"endName\": \"Greg's Oldtowne Tavern\",\"Address\": \"123 Example St\",\"City\": \"Normal\",\"State\": \"IL\",\"ZipCode\": 61761},\"participantListId\": \"1\",\"base_cost\": \"50\",\"total_cost\": \"50\",\"status\": \"planned\",\"isPublic\": false}";
        mockMvc.perform(post("/api/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonEvent))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.organization").value("Phils Buds"))
                .andExpect(jsonPath("$.name").value("St. Patricks Bar Crawl '01"))
                .andExpect(jsonPath("$.description").value("Phil's 21st Birthday Pub Crawl"));
        //todo validate other fields in response
    }



}
