package khpi.khpi_olympiad.service;

public class UsernameAlreadyExists extends RuntimeException {
    public UsernameAlreadyExists(String msg) {
        super(msg);
    }
}
