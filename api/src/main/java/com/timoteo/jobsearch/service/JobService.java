package com.timoteo.jobsearch.service;

import com.timoteo.jobsearch.model.SearchJobsResult;
import com.timoteo.jobsearch.repository.JobSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobSearchRepository jobSearchRepository;

    public Optional<SearchJobsResult> findJobByCityAndCountryCode(String cityName, String countryCode, int pageNumber, int pageSize) {
         return jobSearchRepository.findJobByCityAndCountryCode(cityName, countryCode, pageNumber, pageSize);
    }
}
