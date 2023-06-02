package com.galvanize.events;
import org.springframework.http.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventsIntegrationTests {
    @Autowired
    EventsRepository eventsRepository;

    @Autowired
    TestRestTemplate testRestTemplate;
    RestTemplate restTemplate;

    @BeforeEach
    public void beforeEach() {
        restTemplate = testRestTemplate.getRestTemplate();
        // Create a basic instance of the Apache HttpClient
//        HttpClient httpClient = HttpClientBuilder.create().build();
        // Set the httpClient as the default client used for all requests
//        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        /* Create Test Data */
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            events.add(new Event("AAADDD" + i, "Phils Buds", "St. Patricks Bar Crawl", "Social", "21st Birthday Pub Crawl", 50.01, "planned", false));
        }
        eventsRepository.saveAll(events);
        }

    @AfterEach
    void tearDown() {
        eventsRepository.deleteAll();
        }


    @Test
    void getEventsReturnEventsList() {
        String url = "http://ad0bcd07c990f4a9d9879e71472608fa-1526526031.us-west-2.elb.amazonaws.com/api/activities/getsummary";
        List<Long> eventIds = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L, 4L));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<List<Long>> httpEntity = new HttpEntity<>(eventIds, headers);
        ResponseEntity<ExtEventList> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, ExtEventList.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody().getActivitySummary()).isEqualTo(eventIds);
//    todo add assertion once able to view the api response
    }


}
