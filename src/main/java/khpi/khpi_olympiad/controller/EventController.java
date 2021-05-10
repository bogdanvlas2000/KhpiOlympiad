package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.event.Event;
import khpi.khpi_olympiad.repository.event.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/event")
@AllArgsConstructor
public class EventController {

    private EventRepository eventRepository;

    @GetMapping
    public Event getEvent(@RequestParam Integer id) {
        var event = eventRepository.findById(id).get();
        return event;
    }

    @PostMapping
    public Event createEvent(@RequestBody Map<String, String> body) {
        var event = new Event();
        event.setTitle(body.get("title"));
        event.setDescription(body.get("description"));
        event.setCreatedDate(LocalDateTime.now());
        event.setLastModifiedDate(LocalDateTime.now());
        event.setEventDate(LocalDateTime.parse(body.get("date")));
        return eventRepository.save(event);
    }

    @PutMapping
    public Event putEvent(@RequestBody Map<String, String> body) {
        var event = eventRepository.findById(Integer.parseInt(body.get("id"))).get();
        event.setTitle(body.get("title"));
        event.setDescription(body.get("description"));
        event.setLastModifiedDate(LocalDateTime.now());
        event.setEventDate(LocalDateTime.parse(body.get("date")));
        return eventRepository.save(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventRepository.deleteById(id);
    }
}
