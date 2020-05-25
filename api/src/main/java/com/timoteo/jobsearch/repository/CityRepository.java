package com.timoteo.jobsearch.repository;

import com.timoteo.jobsearch.model.SearchCitiesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
public class CityRepository {

    @Value("${rapidapi.find.city.by.prefix.url}")
    private String findCitiesUrl;

    @Value("${rapidapi.header.host}")
    private String headerHost;

    @Value("${rapidapi.header.key}")
    private String headerKey;

    @Autowired
    private RestTemplate restTemplate;

    public Optional<SearchCitiesResult> findCitiesByPrefix(String cityPrefix, int pageNumber, int pageSize) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Host", headerHost);
        headers.set("X-RapidAPI-Key", headerKey);

        HttpEntity entity = new HttpEntity(headers);

        Integer offset = pageNumber * pageSize;

        ResponseEntity<SearchCitiesResult> response = restTemplate.exchange(
                getFilledURL(cityPrefix, offset), HttpMethod.GET, entity, SearchCitiesResult.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return Optional.of(response.getBody());
        }
        return Optional.empty();

    }

    private String getFilledURL(String cityName, Integer offset) {
        return findCitiesUrl
                .replace("{OFFSET}", offset.toString())
                .replace("{CITY}", cityName);
    }

}
