package com.galvanize.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {

    List<Event> findByCreatorID(String creatorId);

    List<Event> findByIsPublic(Boolean isPublic);
}
