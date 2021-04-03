package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.auth.User;
import khpi.khpi_olympiad.model.event.Event;
import khpi.khpi_olympiad.model.event.Subscription;
import khpi.khpi_olympiad.model.profile.City;
import khpi.khpi_olympiad.model.profile.Profile;
import khpi.khpi_olympiad.model.profile.University;
import khpi.khpi_olympiad.repository.event.EventRepository;
import khpi.khpi_olympiad.repository.event.SubscriptionRepository;
import khpi.khpi_olympiad.repository.profile.CityRepository;
import khpi.khpi_olympiad.repository.profile.UniversityRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DataApiController {
    private CityRepository cityRepository;
    private UniversityRepository universityRepository;
    private EventRepository eventRepository;
    private SubscriptionRepository subscriptionRepository;


    @GetMapping("/cities")
    public Iterable<City> getCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/universities")
    public Iterable<University> getUniversities(@RequestParam("city") String cityUkrName) {
        Iterable<University> universitites = new ArrayList<>();
        if (!cityUkrName.isEmpty()) {
            universitites = universityRepository.findByCityUkrName(cityUkrName);
        } else {
            universitites = universityRepository.findAll();
        }
        return universitites;
    }

    @GetMapping("/subscriptions")
    public Iterable<Subscription> getSubscribers(@RequestParam("eventId") Integer eventId) {
        List<Subscription> subscriptions = subscriptionRepository.findByEventId(eventId);
        return subscriptions;
    }

    @GetMapping("/events")
    public Iterable<Event> getEvents() {
        return eventRepository.findAll();
    }
}
