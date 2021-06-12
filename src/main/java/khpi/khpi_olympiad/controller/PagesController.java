package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.auth.User;
import khpi.khpi_olympiad.repository.auth.UserRepository;
import khpi.khpi_olympiad.repository.event.EventRepository;
import khpi.khpi_olympiad.repository.event.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Base64;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class PagesController {
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private SubscriptionRepository subscriptionRepository;

    @GetMapping
    public String homePage(Model model, Principal prl) {
        if (prl != null) {
            User user = userRepository.findByUsername(prl.getName());
            model.addAttribute("user", user);
            if (user.getRole().getName().equals("ROLE_ADMIN")) {
                model.addAttribute("admin", true);
            }
        }
        return "/pages/home";
    }

    @GetMapping("/events")
    public String eventsPage(Principal prl, Model model) {
        User user = userRepository.findByUsername(prl.getName());
        if (!model.containsAttribute("events")) {
            var events = eventRepository.findAll();
            model.addAttribute("events", events);
        }
        return "/pages/events";
    }

    @GetMapping("/events/{id}")
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
        return "/pages/event";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/pages/users";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        model.addAttribute("user", user);
        return "/pages/profile";
    }
}
