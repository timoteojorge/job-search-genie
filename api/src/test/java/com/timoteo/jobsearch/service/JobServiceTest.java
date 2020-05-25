package com.timoteo.jobsearch.service;

import com.timoteo.jobsearch.BaseTest;
import com.timoteo.jobsearch.model.Job;
import com.timoteo.jobsearch.model.SearchJobsResult;
import com.timoteo.jobsearch.repository.JobSearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class JobServiceTest extends BaseTest {

    @Autowired
    private JobService jobService;

    @MockBean
    private JobSearchRepository jobSearchRepository;

    private SearchJobsResult searchJobsResult;

    @Before
    public void before() {
        Job job = Job.builder().description("some description").id("1").build();
        searchJobsResult = SearchJobsResult.builder().count(1L).results(Arrays.asList(job)).build();
    }

    @Test
    public void when_calling_findJobByCityAndCountryCode_then_return_jobs() {
        Optional<SearchJobsResult> searchJobsResultOptional = Optional.of(searchJobsResult);
        when(jobSearchRepository.findJobByCityAndCountryCode(eq("london"),eq("gb"), anyInt(), anyInt())).thenReturn(searchJobsResultOptional);

        Optional<SearchJobsResult> searchResults = jobService.findJobByCityAndCountryCode("london","gb", 0, 5);
        assertEquals("some description",searchResults.get().getResults().get(0).getDescription());

    }
}
