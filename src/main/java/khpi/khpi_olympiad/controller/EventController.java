package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.Event;
import khpi.khpi_olympiad.model.Note;
import khpi.khpi_olympiad.model.User;
import khpi.khpi_olympiad.repository.EventRepository;
import khpi.khpi_olympiad.repository.NoteRepository;
import khpi.khpi_olympiad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    public String events(Model model) {
        var events = eventRepository.findAll();
        model.addAttribute("events", events);
        return "events/events";
    }

    @GetMapping("/create")
    public String create() {
        return "events/create";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("event") Event event, Principal principal) {

        User user = userRepository.findByUsername(principal.getName());
        user.createNewEvent(event);
        eventRepository.save(event);
        userRepository.save(user);

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
        return "/events/edit";
    }

    @PostMapping("/change")
    public String change(@ModelAttribute(name = "event") Event event, Principal prl) {
        User user = userRepository.findByUsername(prl.getName());
        event.setCreatedByUser(user);
        eventRepository.save(event);
        userRepository.save(user);
        return "redirect:/events/all";
    }

    @PostMapping("/search")
    public String searchByWord(@RequestParam("word") String word, Model model, Principal prpl) {
        var user = userRepository.findByUsername(prpl.getName());

        word = "%" + word + "%";
        var events = eventRepository.search(user.getId(), word);
        model.addAttribute("notes", events);
        return "/events/events";
    }

}