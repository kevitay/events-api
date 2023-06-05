package com.galvanize.events;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/event")
public class EventImageController {
    EventImageService eventImageService;

    public EventImageController(EventImageService eventImageService) {
        this.eventImageService = eventImageService;
    }
    @PostMapping("/{id}/eventImages")
    public EventImage addImage(@PathVariable Long id, @RequestParam("eventImg") MultipartFile newImage){
        return eventImageService.addEventImage(id, newImage);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidImageFound(InvalidImageException e) {
    }
}
