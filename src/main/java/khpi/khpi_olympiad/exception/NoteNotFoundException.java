package khpi.khpi_olympiad.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(int id) {
        super("Not found note with id: " + id);
    }
}
