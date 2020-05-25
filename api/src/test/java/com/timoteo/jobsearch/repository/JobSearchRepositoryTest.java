package com.timoteo.jobsearch.repository;

import com.timoteo.jobsearch.BaseTest;
import com.timoteo.jobsearch.model.Job;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class JobSearchRepositoryTest extends BaseTest {

    @Autowired
    private JobSearchRepository jobSearchRepository;

    @MockBean
    private RestTemplate restTemplate;

    SearchJobsResult searchJobsResult;

    @Before
    public void before() {
        Job job = Job.builder().description("some description").id("1").build();
        searchJobsResult = SearchJobsResult.builder().count(1L).results(Arrays.asList(job)).build();
    }

    @Test
    public void when_calling_findJobByCityAndCountryCode_then_return_jobs() {
        ResponseEntity<SearchJobsResult> response = ResponseEntity.ok(searchJobsResult);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(SearchJobsResult.class))).thenReturn(response);
        Optional<SearchJobsResult> searchJobsResultOptional = jobSearchRepository.findJobByCityAndCountryCode("london", "gb", 0, 5);
        assertEquals("some description",searchJobsResultOptional.get().getResults().get(0).getDescription());
    }

}
