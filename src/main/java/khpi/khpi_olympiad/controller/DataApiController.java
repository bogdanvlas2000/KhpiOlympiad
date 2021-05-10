package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.auth.User;
import khpi.khpi_olympiad.model.event.Event;
import khpi.khpi_olympiad.model.event.Subscription;
import khpi.khpi_olympiad.model.profile.City;
import khpi.khpi_olympiad.model.profile.Profile;
import khpi.khpi_olympiad.model.profile.University;
import khpi.khpi_olympiad.repository.auth.UserRepository;
import khpi.khpi_olympiad.repository.event.EventRepository;
import khpi.khpi_olympiad.repository.event.SubscriptionRepository;
import khpi.khpi_olympiad.repository.profile.CityRepository;
import khpi.khpi_olympiad.repository.profile.UniversityRepository;
import khpi.khpi_olympiad.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DataApiController {
    private CityRepository cityRepository;
    private UniversityRepository universityRepository;
    private EventRepository eventRepository;
    private SubscriptionRepository subscriptionRepository;
    private UserRepository userRepository;
    private EmailSenderService emailSenderService;

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
    public Iterable<Subscription> getSubscriptions(@RequestParam("eventId") Integer eventId) {
        List<Subscription> subscriptions = subscriptionRepository.findByEventId(eventId);
        return subscriptions;
    }

    @GetMapping("/subscribers")
    public Iterable<User> getSubscribers(@RequestParam("eventId") Integer eventId) {
        List<Subscription> subscriptions = subscriptionRepository.findByEventId(eventId);
        List<Integer> userIds = subscriptions.stream().map(s -> s.getUserId()).collect(Collectors.toList());
        Iterable<User> subscribers = userRepository.findAllById(userIds);
        return subscribers;
    }

    @GetMapping("/events")
    public List<Event> getEvents(@RequestParam(name = "word", required = false) String word) {
        List<Event> events = new ArrayList<>();
        if (word == null) {
            for (Event e : eventRepository.findAll()) {
                events.add(e);
            }
        } else {
            events = eventRepository.search("%" + word + "%");
        }
        Collections.reverse(events);
        return events;
    }



    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(name = "word", required = false) String word) {
        if (word == null) {
            var users = userRepository.findReadyUsers();
            return users;
        } else {
            return userRepository.search("%" + word + "%");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam("user_id") Integer userId) {
        var user = userRepository.findById(userId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ready")
    public Boolean isReady(Principal prl) {
        if (prl != null) {
            var user = userRepository.findByUsername(prl.getName());
            return user.isReady() || user.getRole().getName().equals("ROLE_ADMIN");
        }
        return true;
    }

    @GetMapping("/subscription")
    public ResponseEntity<Subscription> getSubscription(
            @RequestParam(name = "eventId") Integer eventId,
            Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        var subscription = subscriptionRepository.findByUserIdAndEventId(user.getId(), eventId);
        if (subscription != null) {
            return new ResponseEntity<>(subscription, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/subscription")
    public ResponseEntity<Subscription> subscribe(
            @RequestBody Map<String, Integer> body,
            Principal prl) {
        Integer eventId = body.get("eventId");
        var user = userRepository.findByUsername(prl.getName());
        var event = eventRepository.findById(eventId).get();
        if (user == null || event == null) {
        }
        user.subscribe(event);
        userRepository.save(user);
        var subscription = subscriptionRepository.findByUserIdAndEventId(user.getId(), eventId);
        subscription.setSubscriptionDate(LocalDateTime.now());
        subscriptionRepository.save(subscription);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @DeleteMapping("/subscription")
    public void unsubscribe(@RequestBody Map<String, Integer> body, Principal prl) {

        Integer eventId = body.get("eventId");
        var user = userRepository.findByUsername(prl.getName());
        var event = eventRepository.findById(eventId).get();

        var subscription = subscriptionRepository.findByUserIdAndEventId(user.getId(), eventId);
        if (subscription != null) {
            subscriptionRepository.delete(subscription);
        }
    }

    @PostMapping("/email")
    public void sendMails(@RequestBody Map<String, Object> body) {
        String message = (String) body.get("message");
        ArrayList<String> emails = (ArrayList<String>) body.get("emails");
        String[] arr = new String[emails.size()];
        for (int i = 0; i < emails.size(); i++) {
            arr[i] = emails.get(i);
        }
        var mail = new SimpleMailMessage();
        mail.setTo(arr);
        mail.setText(message);
        mail.setSubject("Новое сообщение от кафедры ВТП!");
        mail.setFrom("khpi@Mail.com");

        emailSenderService.sendEmail(mail);
    }
}
