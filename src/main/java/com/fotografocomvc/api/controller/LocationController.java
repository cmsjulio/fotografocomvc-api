package com.fotografocomvc.api.controller;

import com.fotografocomvc.api.http.resources.request.LocationRequest;
import com.fotografocomvc.domain.model.BaseUser;
import com.fotografocomvc.domain.model.Location;
import com.fotografocomvc.domain.service.BaseUserService;
import com.fotografocomvc.domain.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(tags = {"Location"}, description = "Findall location")
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Location>> findAllLocation() {
        List<Location> locationList = locationService.findAllLocation();
        return ResponseEntity.ok(locationList);
    }

    @Operation(tags = { "Location" }, description = "Find location by id")
    @GetMapping("{locationId}")
    @ResponseBody
    public ResponseEntity<Location> findLocationById (@PathVariable ("locationId") Long locationId) {
        Location location =  locationService.findById(locationId).get();
        return ResponseEntity.ok(location);
    }
}
