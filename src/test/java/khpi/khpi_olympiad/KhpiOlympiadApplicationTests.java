package khpi.khpi_olympiad;

import khpi.khpi_olympiad.model.profile.City;
import khpi.khpi_olympiad.model.profile.University;
import khpi.khpi_olympiad.repository.event.EventRepository;
import khpi.khpi_olympiad.repository.auth.UserRepository;
import khpi.khpi_olympiad.repository.profile.CityRepository;
import khpi.khpi_olympiad.repository.profile.UniversityRepository;
import khpi.khpi_olympiad.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
class KhpiOlympiadApplicationTests {
    private UniversityRepository universityRepository;

    private CityRepository cityRepository;

    private EventRepository eventRepository;

    private UserRepository userRepository;

    private EmailSenderService emailSenderService;

    @Autowired
    public KhpiOlympiadApplicationTests(UniversityRepository universityRepository, CityRepository cityRepository, EventRepository eventRepository, UserRepository userRepository, EmailSenderService emailSenderService) {
        this.universityRepository = universityRepository;
        this.cityRepository = cityRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    @Test
    @Disabled
    void readUniversitiesFromFile() {
        var path = "./src/main/resources/static/files/universities.xlsx";
        try {
            XSSFWorkbook document = new XSSFWorkbook(new FileInputStream(path));
            XSSFSheet sheet = document.getSheet("first");
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                String ukrName = row.getCell(0).toString();
                String ukrShortName = row.getCell(3).toString();
                String engName = row.getCell(4).toString();
                String cityName = row.getCell(13).toString();

                City city = cityRepository.findByUkrName(cityName);
                if (city == null) {
                    city = cityRepository.save(new City(cityName));
                    System.out.println(city.getUkrName());
                }
                University university = universityRepository.findByEngName(engName);
                if (university == null) {
                    university = new University(engName, ukrName, ukrShortName, city);
                    university = universityRepository.save(university);
                }
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    void findUniversityTest() {
        String name = "НТУ \"ХПІ\"";
        var university = universityRepository.findByUkrShortName(name);
        System.out.println(university);
    }


    @Test
    void sendEmailTest(){
        var mail = new SimpleMailMessage();
        mail.setText("Some random test");
        mail.setFrom("Bogdan");
        mail.setTo("Kornei");
        mail.setSubject("Test mail");

        System.out.println(Thread.currentThread().getName());
        System.out.println("Begin sending....");
        emailSenderService.sendEmail(mail);
        System.out.println("Do something else....!");
    }
}
