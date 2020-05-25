package com.timoteo.jobsearch.controller;


import com.timoteo.jobsearch.BaseTest;
import com.timoteo.jobsearch.model.City;
import com.timoteo.jobsearch.model.Metadata;
import com.timoteo.jobsearch.model.SearchCitiesResult;
import com.timoteo.jobsearch.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerTest extends BaseTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private CityService cityService;

    SearchCitiesResult searchCitiesResult;

    @Before
    public void before() {
        City city = City.builder().countryCode("GB").name("London").build();
        Metadata metadata = Metadata.builder().totalCount(1).build();
        searchCitiesResult = SearchCitiesResult.builder().data(Arrays.asList(city)).metadata(metadata).build();
    }

    @Test
    public void when_calling_getCities_then_return_cities() throws Exception {

        Optional<SearchCitiesResult> searchCitiesResultOptional = Optional.of(searchCitiesResult);
        when(cityService.findCitiesByPrefix(eq("london"), anyInt(), anyInt())).thenReturn(searchCitiesResultOptional);

        mvc.perform(get("/cities?cityPrefix=london&page=0&size=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].countryCode").value("GB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("London"));
    }


}
