package khpi.khpi_olympiad.controller;

import java.security.Principal;
import java.util.List;

import khpi.khpi_olympiad.model.Note;
import khpi.khpi_olympiad.model.User;
import khpi.khpi_olympiad.repository.NoteRepository;
import khpi.khpi_olympiad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/notes")
public class NoteController {
    private NoteRepository noteRepository;

    private UserRepository userRepository;

    @Autowired
    public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public String notes(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<Note> notes = user.getNotes();
        model.addAttribute("notes", notes);
        return "notes/notes";
    }

    @GetMapping("/create")
    public String create() {
        return "notes/create_note";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("note") Note note, Principal principal) {

        User user = userRepository.findByUsername(principal.getName());
        user.saveNote(note);
        noteRepository.save(note);
        userRepository.save(user);

        return "redirect:/notes/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        noteRepository.deleteById(id);
        return "redirect:/notes/all";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Note note = noteRepository.findById(id).get();
        model.addAttribute("note", note);
        return "/notes/edit_note";
    }

    @PostMapping("/change")
    public String change(@ModelAttribute(name = "note") Note note, Principal prl) {
        User user = userRepository.findByUsername(prl.getName());
        note.setUser(user);
        noteRepository.save(note);
        userRepository.save(user);
        return "redirect:/notes/all";
    }

    @PostMapping("/search")
    public String searchByWord(@RequestParam("word") String word, Model model, Principal prpl) {
        var user = userRepository.findByUsername(prpl.getName());

        word = "%" + word + "%";
        var notes = noteRepository.search(user.getId(), word);
        model.addAttribute("notes", notes);
        return "/notes/notes";
    }

}
