package com.rw.apps.starter.location.api;

import com.rw.apps.starter.location.model.CountryInfo;
import com.rw.apps.starter.location.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/location")
class LocationApiV1 {
    private final LocationService locationService;

    LocationApiV1(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("country-info")
    CountryInfo getCountryInfo() {
        return locationService.resolveCountryInfo();
    }
}
