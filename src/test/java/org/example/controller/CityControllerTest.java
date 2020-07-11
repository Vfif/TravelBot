package org.example.controller;

import org.example.AppTest;
import org.example.model.City;
import org.example.repository.CityRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTest.class)
public class CityControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CityRepository cityRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void findAll() throws Exception {
        List<City> cities = new ArrayList<>();
        when(cityRepository.findAll()).thenReturn(cities);

        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(cities.size()));

        verify(cityRepository).findAll();
    }

    @Test
    public void save() throws Exception {
        String json = "{\n" +
                "  \"name\": \"Beijing\",\n" +
                "  \"info\": \"Beautiful city\"\n" +
                "}";

        when(cityRepository.save(any(City.class))).thenAnswer(i -> i.getArgument(0));
        mockMvc.perform(post("/cities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("Beijing")))
                .andExpect(jsonPath("$.info", Matchers.is("Beautiful city")));
        verify(cityRepository).save(any(City.class));
    }

    @Test
    public void editNotFound() throws Exception {
        String json = "{\n" +
                "  \"info\": \"Beautiful city to walk\"\n" +
                "}";

        when(cityRepository.findById(anyInt())).thenReturn(Optional.empty());

        mockMvc.perform(put("/cities/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound());
        verify(cityRepository).findById(anyInt());
    }

    @Test
    public void deleteCity() throws Exception {
        doNothing().when(cityRepository).deleteById(anyInt());
        mockMvc.perform(delete("/cities/4"))
                .andExpect(status().isOk());
        verify(cityRepository).deleteById(anyInt());
    }
}