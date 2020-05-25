package com.timoteo.jobsearch.repository;

import com.timoteo.jobsearch.model.SearchJobsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@Repository
public class JobSearchRepository {

    @Value("${adzuna.search.by.city.url}")
    private String searchByCityURL;

    @Autowired
    private RestTemplate restTemplate;

    public Optional<SearchJobsResult> findJobByCityAndCountryCode(String cityName, String countryCode, Integer page, Integer resultsPerPage) {
        try{
            cityName = cityName.toLowerCase();
            HttpHeaders headers = new HttpHeaders();
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity<SearchJobsResult> response = restTemplate.exchange(getFilledURL(cityName, countryCode, page + 1, resultsPerPage), HttpMethod.GET, entity, SearchJobsResult.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return Optional.of(response.getBody());
            }
            return Optional.empty();
        }catch (HttpClientErrorException e) {
            return Optional.empty();
        }
    }

    private String getFilledURL(String cityName, String countryCode, Integer page, Integer resultsPerPage) {
        return searchByCityURL
                .replace("{PAGE}", page.toString())
                .replace("{RESULTS_PER_PAGE}", resultsPerPage.toString())
                .replace("{COUNTRY_CODE}", countryCode.toLowerCase())
                .replace("{CITY}", cityName);
    }
}
