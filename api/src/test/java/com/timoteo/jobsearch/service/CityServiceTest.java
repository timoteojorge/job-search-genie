package com.timoteo.jobsearch.service;

import com.timoteo.jobsearch.BaseTest;
import com.timoteo.jobsearch.model.City;
import com.timoteo.jobsearch.model.Metadata;
import com.timoteo.jobsearch.model.SearchCitiesResult;
import com.timoteo.jobsearch.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class CityServiceTest extends BaseTest {

    @Autowired
    private CityService cityService;

    @MockBean
    private CityRepository cityRepository;

    private SearchCitiesResult searchCitiesResult;

    @Before
    public void before() {
        City city = City.builder().country("GB").name("London").build();
        Metadata metadata = Metadata.builder().totalCount(1).build();
        searchCitiesResult = SearchCitiesResult.builder().data(Arrays.asList(city)).metadata(metadata).build();
    }

    @Test
    public void when_calling_findCitiesByPrefix_then_return_cities() {
        Optional<SearchCitiesResult> searchCitiesResultOptional = Optional.of(searchCitiesResult);
        when(cityRepository.findCitiesByPrefix(eq("london"), anyInt(), anyInt())).thenReturn(searchCitiesResultOptional);

        Optional<SearchCitiesResult> searchResults = cityService.findCitiesByPrefix("london", 0, 5);
        assertEquals(searchCitiesResult.getData().get(0).getName(),searchResults.get().getData().get(0).getName());

    }
}
