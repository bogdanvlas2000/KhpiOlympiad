package khpi.khpi_olympiad.controller;

import khpi.khpi_olympiad.model.profile.City;
import khpi.khpi_olympiad.model.profile.University;
import khpi.khpi_olympiad.repository.profile.CityRepository;
import khpi.khpi_olympiad.repository.profile.UniversityRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DataApi {
    private CityRepository cityRepository;
    private UniversityRepository universityRepository;

    public DataApi(CityRepository cityRepository, UniversityRepository universityRepository) {
        this.cityRepository = cityRepository;
        this.universityRepository = universityRepository;
    }

    @GetMapping("/cities")
    public Iterable<City> getCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/universities")
    public Iterable<University> getUniversities(@RequestParam("city") String cityUkrName) {
        var universitites = universityRepository.findByCityUkrName(cityUkrName);
        return universitites;
    }
}
