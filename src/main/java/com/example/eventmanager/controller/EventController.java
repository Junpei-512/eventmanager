package com.example.eventmanager.controller;

import com.example.eventmanager.entity.Event;
import com.example.eventmanager.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // イベントの全リストを取得
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // イベントをIDで取得
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        return ResponseEntity.ok(event);
    }

    // 新しいイベントを作成
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    // イベント情報を更新
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }

        event.setName(eventDetails.getName());
        event.setDate(eventDetails.getDate());
        event.setLocation(eventDetails.getLocation());
        event.setCapacity(eventDetails.getCapacity());
        event.setDescription(eventDetails.getDescription());

        Event updatedEvent = eventRepository.save(event);
        return ResponseEntity.ok(updatedEvent);
    }

    // イベントを削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
