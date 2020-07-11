package org.example.service;

import org.example.AppTest;
import org.example.exception.CityNotFoundException;
import org.example.model.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest(classes = AppTest.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Sql({"schema.sql", "data.sql"})
public class CityServiceTest {
    @Autowired
    private CityService cityService;

    @Test
    void findAll() {
        List<City> roomList = cityService.findAll();
        Assertions.assertEquals(roomList.size(), 2);
    }

    @Test
    void save() {
        City city = City.builder()
                .name("hello")
                .info("Belarus")
                .build();
        cityService.save(city);
        List<City> cityList = cityService.findAll();
        Assertions.assertEquals(3, cityList.size());
    }

    @Test
    void edit() {
        int id = 1;
        String info = "New city";
        City city = City.builder().info(info).build();
        cityService.edit(id, city);
        City cityAfterEditing = cityService.findAll()
                .stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElseThrow(CityNotFoundException::new);

        Assertions.assertEquals(info, cityAfterEditing.getInfo());
    }

    @Test
    void delete() {
        cityService.delete(1);
        List<City> cityList = cityService.findAll();
        Assertions.assertEquals(1, cityList.size());
    }
}
