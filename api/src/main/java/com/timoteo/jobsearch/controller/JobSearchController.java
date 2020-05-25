package com.timoteo.jobsearch.controller;

import com.timoteo.jobsearch.model.Job;
import com.timoteo.jobsearch.model.SearchJobsResult;
import com.timoteo.jobsearch.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/jobs")
public class JobSearchController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<Page<Job>> findJobByCityAndCountry(@RequestParam String cityName,
                                                             @RequestParam String countryCode,
                                                             @RequestParam int page,
                                                             @RequestParam int size) {
        Optional<SearchJobsResult> search = jobService.findJobByCityAndCountryCode(cityName, countryCode, page, size);
        if (search.isPresent()) {
            SearchJobsResult searchJobsResult = search.get();
            return ResponseEntity.ok(new PageImpl<>(searchJobsResult.getResults(), PageRequest.of(page, size), searchJobsResult.getCount()));
        }
        return ResponseEntity.notFound().build();
    }

}
