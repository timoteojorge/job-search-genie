package com.timoteo.jobsearch.service;

import com.timoteo.jobsearch.model.SearchCitiesResult;
import com.timoteo.jobsearch.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public Optional<SearchCitiesResult> findCitiesByPrefix(String cityPrefix, int pageNumber, int pageSize) {
        return cityRepository.findCitiesByPrefix(cityPrefix, pageNumber, pageSize);
    }
}
