package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.profile.City;
import khpi.khpi_olympiad.model.profile.University;
import khpi.khpi_olympiad.repository.profile.CityRepository;
import khpi.khpi_olympiad.repository.profile.UniversityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DataApiController {
    private CityRepository cityRepository;
    private UniversityRepository universityRepository;

    public DataApiController(CityRepository cityRepository, UniversityRepository universityRepository) {
        this.cityRepository = cityRepository;
        this.universityRepository = universityRepository;
    }

    @GetMapping("/cities")
    public Iterable<City> getCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/universities")
    public Iterable<University> getUniversities(@RequestParam("city") String cityUkrName) {
        Iterable<University> universitites = new ArrayList<>();
        if (!cityUkrName.isEmpty()) {
            universitites = universityRepository.findByCityUkrName(cityUkrName);
        } else {
            universitites = universityRepository.findAll();
        }
        return universitites;
    }
}
