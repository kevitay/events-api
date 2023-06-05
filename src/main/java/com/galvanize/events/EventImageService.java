package com.galvanize.events;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class EventImageService {

    EventsRepository eventsRepository;
    EventImageRepository eventImageRepository;

    public EventImageService(EventsRepository eventsRepository, EventImageRepository eventImageRepository) {
        this.eventsRepository = eventsRepository;
        this.eventImageRepository = eventImageRepository;
    }

    public EventImage addEventImage(Long id, MultipartFile newImage) {
        Optional<Event> event = eventsRepository.findById(id);
        if (event.isPresent()) {
            try {
                EventImage eventImage = new EventImage(event.get());
                byte[] imageBytes = newImage.getBytes();
                eventImage.setData(imageBytes);
                return eventImageRepository.save(eventImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
