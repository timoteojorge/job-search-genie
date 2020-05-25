package com.timoteo.jobsearch.controller;


import com.timoteo.jobsearch.BaseTest;
import com.timoteo.jobsearch.model.City;
import com.timoteo.jobsearch.model.Job;
import com.timoteo.jobsearch.model.Metadata;
import com.timoteo.jobsearch.model.SearchCitiesResult;
import com.timoteo.jobsearch.model.SearchJobsResult;
import com.timoteo.jobsearch.service.CityService;
import com.timoteo.jobsearch.service.JobService;
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

public class JobSearchControllerTest extends BaseTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private JobService jobService;

    SearchJobsResult searchJobsResult;

    @Before
    public void before() {
        Job job = Job.builder().description("some description").id("1").build();
        searchJobsResult = SearchJobsResult.builder().count(1L).results(Arrays.asList(job)).build();
    }

    @Test
    public void when_calling_findJobByCityAndCountry_then_return_jobs() throws Exception {

        Optional<SearchJobsResult> searchJobsResultOptional = Optional.of(searchJobsResult);
        when(jobService.findJobByCityAndCountryCode(eq("london"), eq("gb"), anyInt(),anyInt())).thenReturn(searchJobsResultOptional);

        mvc.perform(get("/jobs?cityName=london&countryCode=gb&page=0&size=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].description").value("some description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value("1"));
    }


}
