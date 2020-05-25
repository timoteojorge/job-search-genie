package com.timoteo.jobsearch.controller;

import com.timoteo.jobsearch.model.City;
import com.timoteo.jobsearch.model.SearchCitiesResult;
import com.timoteo.jobsearch.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public ResponseEntity<Page<City>> getCities(@RequestParam String cityPrefix,
                                                @RequestParam int page,
                                                @RequestParam int size) {
        Optional<SearchCitiesResult> searchCitiesResult = cityService.findCitiesByPrefix(cityPrefix, page, size);
        if (searchCitiesResult.isPresent()) {
            SearchCitiesResult citiesResult = searchCitiesResult.get();
            return ResponseEntity.ok(new PageImpl<>(citiesResult.getData(), PageRequest.of(page, size), citiesResult.getMetadata().getTotalCount()));
        }
        return ResponseEntity.notFound().build();

    }

}
