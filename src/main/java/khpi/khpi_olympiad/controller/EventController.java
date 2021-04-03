package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.auth.User;
import khpi.khpi_olympiad.model.event.Event;
import khpi.khpi_olympiad.model.event.Subscription;
import khpi.khpi_olympiad.repository.auth.UserRepository;
import khpi.khpi_olympiad.repository.event.EventRepository;
import khpi.khpi_olympiad.repository.event.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/events")
public class EventController {
    private EventRepository eventRepository;

    private UserRepository userRepository;

    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public EventController(
            EventRepository eventRepository,
            UserRepository userRepository,
            SubscriptionRepository subscriptionRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @GetMapping("/all")
    public String events(Principal prl, Model model) {
        User user = userRepository.findByUsername(prl.getName());
        if (!model.containsAttribute("events")) {
            var events = eventRepository.findAll();
            model.addAttribute("events", events);
        }
        if (user.getRole().getName().equals("ROLE_ADMIN")) {
            model.addAttribute("subscriptionRepo", subscriptionRepository);
        }
        return "events/events";
    }

    @GetMapping("/{id}")
    public String eventPage(@PathVariable("id") int eventId, Model model, Principal prl) {

        var event = eventRepository.findById(eventId).get();
        var subscriptions = subscriptionRepository.findByEventId(event.getId());

        var subscribersIds = subscriptions.stream().map(s -> s.getUserId()).collect(Collectors.toList());

        var subscribers = userRepository.findAllById(subscribersIds);

        var user = userRepository.findByUsername(prl.getName());

        subscribers.forEach(subscriber -> {
            if (subscriber.equals(user)) {
                model.addAttribute("subscribed", true);
            }
        });

        model.addAttribute("event", event);
        model.addAttribute("subscribers", subscribers);
        return "/events/event";
    }

    @GetMapping("/search")
    public String searchAll(@RequestParam("word") String word, RedirectAttributes attr) {
        word = "%" + word + "%";
        var events = eventRepository.search(word);
        attr.addFlashAttribute("events", events);
        return "redirect:/events/all";
    }

    @PostMapping("/{event_id}/subscribe")
    public String subscribe(@PathVariable("event_id") Integer eventId, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        var event = eventRepository.findById(eventId).get();
        if (subscriptionRepository.findByUserIdAndEventId(user.getId(), eventId) == null) {
            user.subscribe(event);
            userRepository.save(user);

            var subscription = subscriptionRepository.findByUserIdAndEventId(user.getId(), eventId);
            subscription.setSubscriptionDate(LocalDateTime.now());
            subscriptionRepository.save(subscription);
        }
        return "redirect:/events/" + event.getId();
    }

    @PostMapping("/{event_id}/unsubscribe")
    public String unsubscribe(@PathVariable("event_id") Integer eventId, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        var event = eventRepository.findById(eventId).get();
        var subscription = subscriptionRepository.findByUserIdAndEventId(user.getId(), eventId);
        if (subscription != null) {
            user.unsubscribe(event);
            userRepository.save(user);
        }
        return "redirect:/events/" + event.getId();
    }

    //admin methods

    @GetMapping("/create")
    public String create() {
        return "/events/create";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("event") Event event, @RequestParam("date") String date) {

        event.setCreatedDate(LocalDateTime.now());
        event.setLastModifiedDate(LocalDateTime.now());
        event.setEventDate(LocalDateTime.parse(date));
        eventRepository.save(event);

        return "redirect:/events/all";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        eventRepository.deleteById(id);
        return "redirect:/events/all";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Event event = eventRepository.findById(id).get();
        model.addAttribute("event", event);
        model.addAttribute("createdDate", event.getCreatedDate());
        model.addAttribute("lastModifiedDate", event.getLastModifiedDate());

        return "/events/edit";
    }

    @PostMapping("/change")
    public String change(@ModelAttribute(name = "event") Event event,
                         @RequestParam("cr_date") String createdDate,
                         @RequestParam("l_mod_date") String lastModifiedDate,
                         @RequestParam("date") String eventDate) {
        event.setCreatedDate(LocalDateTime.parse(createdDate));
        event.setLastModifiedDate(LocalDateTime.parse(lastModifiedDate));
        event.setEventDate(LocalDateTime.parse(eventDate));
        if (!event.equals(eventRepository.findById(event.getId()).get())) {
            event.setLastModifiedDate(LocalDateTime.now());
        }
        eventRepository.save(event);
        return "redirect:/events/" + event.getId();
    }


}
