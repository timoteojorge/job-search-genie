package com.timoteo.jobsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job {
    private Category category;
    private String adref;
    private String redirect_url;
    private String description;
    private String id;
    private String salary_is_predicted;
    private Company company;
    private float salary_min;
    private float latitude;
    private float salary_max;
    private String title;
    private String created;
    private Location location;
    private float longitude;

}

