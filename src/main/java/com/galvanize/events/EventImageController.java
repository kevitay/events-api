package com.galvanize.events;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/event")
public class EventImageController {
    EventImageService eventImageService;

    public EventImageController(EventImageService eventImageService) {
        this.eventImageService = eventImageService;
    }

    @PostMapping("/{id}/eventImages")
    public EventImage addImage(@PathVariable Long id, @RequestParam("eventImg") MultipartFile newImage) {
        return eventImageService.addEventImage(id, newImage);
    }

    @GetMapping("/{id}/eventImages")
    public List<EventImage> getEventImages(@PathVariable Long id) {
        return eventImageService.getEventImages(id);
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidImageFound(InvalidImageException e) {
    }
}
