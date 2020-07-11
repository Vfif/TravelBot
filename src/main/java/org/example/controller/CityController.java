package org.example.controller;

import org.example.model.City;
import org.example.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    private static final String SUCCESSFULLY_DELETE = "Successfully delete";
    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> findAll() {
        return cityService.findAll();
    }

    @PostMapping
    public City save(@RequestBody City city) {
        return cityService.save(city);
    }

    @PutMapping("/{id}")
    public City edit(@PathVariable int id,
                     @RequestBody City city) {
        return cityService.edit(id, city);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        cityService.delete(id);
        return ResponseEntity.ok(SUCCESSFULLY_DELETE);
    }
}
