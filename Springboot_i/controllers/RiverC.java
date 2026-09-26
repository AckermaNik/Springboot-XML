package ht452.ws.rest_springboot.controllers;

import ht452.ws.rest_springboot.service.GeoService;

import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/river")
public class RiverC {

    private final GeoService geo;

    public RiverC(GeoService geo) {	// called automatically by Spring
        this.geo = geo;
    }

    @GetMapping("/countries/{name}")
    public List<String> passesThroughCountries(@PathVariable String name) {
        return geo.get_river_passes_through_countries(name);
    }

    @GetMapping("/outfall/{name}")
    public String outfall(@PathVariable String name) {
        return geo.get_river_outfall(name);
    }
}