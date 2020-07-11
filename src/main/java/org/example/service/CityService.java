package org.example.service;

import org.example.exception.CityNotFoundException;
import org.example.model.City;
import org.example.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public Optional<City> findByName(String name) {
        return cityRepository.findByName(name);
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    public City edit(int id, City newCity) {
        City existingCity = cityRepository
                .findById(id)
                .orElseThrow(() -> new CityNotFoundException("City not found"));

        existingCity.setInfo(newCity.getInfo());
        return cityRepository.save(existingCity);
    }

    public void delete(int id) {
        cityRepository.deleteById(id);
    }
}
