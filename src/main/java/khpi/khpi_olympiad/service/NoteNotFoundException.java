package khpi.khpi_olympiad.service;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(int id) {
        super("Not found note with id: " + id);
    }
}
