package khpi.khpi_olympiad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KhpiOlympiadApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhpiOlympiadApplication.class, args);
    }

}
