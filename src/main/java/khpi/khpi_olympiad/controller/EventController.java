package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.Event;
import khpi.khpi_olympiad.model.auth.User;
import khpi.khpi_olympiad.repository.EventRepository;
import khpi.khpi_olympiad.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
@RequestMapping("/events")
public class EventController {
    private EventRepository eventRepository;

    private UserRepository userRepository;

    @Autowired
    public EventController(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public String events(Principal prl, Model model) {
        User user = userRepository.findByUsername(prl.getName());
        if (!model.containsAttribute("events")) {
            var events = eventRepository.findAll();
            model.addAttribute("events", events);
        }
        return "events/events";
    }

    @GetMapping("/search")
    public String searchAll(@RequestParam("word") String word, RedirectAttributes attr) {
        word = "%" + word + "%";
        var events = eventRepository.search(word);
        attr.addFlashAttribute("events", events);
        return "redirect:/events/all";
    }

    @PostMapping("/subscribe/{event_id}")
    public String subscribe(@PathVariable("event_id") Integer eventId, Principal prl) {
        var user = userRepository.findByUsername(prl.getName());
        var event = eventRepository.findById(eventId).get();
        user.subscribe(event);
        userRepository.save(user);
        return "redirect:/events/all";
    }

    //admin methods

    @GetMapping("/create")
    public String create() {
        return "/events/create";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("event") Event event) {

        event.setCreatedDate(LocalDateTime.now());
        event.setLastModifiedDate(LocalDateTime.now());
        eventRepository.save(event);

        return "redirect:/events/all";
    }

    @GetMapping("/delete/{id}")
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
                         @RequestParam("l_mod_date") String lastModifiedDate) {
        event.setCreatedDate(LocalDateTime.parse(createdDate));
        event.setLastModifiedDate(LocalDateTime.parse(lastModifiedDate));
        if (!event.equals(eventRepository.findById(event.getId()).get())) {
            event.setLastModifiedDate(LocalDateTime.now());
        }
        eventRepository.save(event);
        return "redirect:/events/all";
    }
}
