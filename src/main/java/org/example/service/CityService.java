package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.CityNotFoundException;
import org.example.model.City;
import org.example.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> findAll() {
        log.info("Find all cities");
        return cityRepository.findAll();
    }

    public Optional<City> findByName(String name) {
        log.info("Find information about city by name");
        return cityRepository.findByName(name);
    }

    public City save(City city) {
        log.info("Save city: name: " + city.getName() + ", info: " + city.getInfo());
        return cityRepository.save(city);
    }

    public City edit(int id, City newCity) {
        City existingCity = cityRepository
                .findById(id)
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        existingCity.setInfo(newCity.getInfo());

        log.info("Edit city: name: " + existingCity.getName() + ", info: " + existingCity.getInfo());
        return cityRepository.save(existingCity);
    }

    public void delete(int id) {
        log.info("Delete city by id = " + id);
        cityRepository.deleteById(id);
    }
}
