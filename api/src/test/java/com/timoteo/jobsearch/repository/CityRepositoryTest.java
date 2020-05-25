package com.timoteo.jobsearch.repository;

import com.timoteo.jobsearch.BaseTest;
import com.timoteo.jobsearch.model.City;
import com.timoteo.jobsearch.model.Job;
import com.timoteo.jobsearch.model.Metadata;
import com.timoteo.jobsearch.model.SearchCitiesResult;
import com.timoteo.jobsearch.model.SearchJobsResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class CityRepositoryTest extends BaseTest {

    @Autowired
    private CityRepository cityRepository;

    @MockBean
    private RestTemplate restTemplate;

    SearchCitiesResult searchCitiesResult;

    @Before
    public void before() {
        City city = City.builder().country("GB").name("London").build();
        Metadata metadata = Metadata.builder().totalCount(1).build();
        searchCitiesResult = SearchCitiesResult.builder().data(Arrays.asList(city)).metadata(metadata).build();
    }

    @Test
    public void when_calling_findJobByCityAndCountryCode_then_return_jobs() {
        ResponseEntity<SearchCitiesResult> response = ResponseEntity.ok(searchCitiesResult);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(SearchCitiesResult.class))).thenReturn(response);
        Optional<SearchCitiesResult> searchCitiesResultOptional = cityRepository.findCitiesByPrefix("london",  0, 5);
        assertEquals("London",searchCitiesResultOptional.get().getData().get(0).getName());
    }

}
