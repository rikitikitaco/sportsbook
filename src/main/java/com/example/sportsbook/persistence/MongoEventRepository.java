package com.example.sportsbook.persistence;

import com.example.sportsbook.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoEventRepository extends MongoRepository<Event, String> {

    Optional<Event> findByEventId(String id);

}
