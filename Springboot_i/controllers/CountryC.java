package ht452.ws.rest_springboot.controllers;

import ht452.ws.rest_springboot.service.GeoService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/country")
public class CountryC {

    private final GeoService geo;

    public CountryC(GeoService geo) {	// called automatically by Spring
        this.geo = geo;
    }

    @GetMapping("/name/{code}")
    public String name(@PathVariable String code) {
        return geo.get_country_name(code);
    }

    @GetMapping("/population/{code}")
    public long population(@PathVariable String code) {
        return geo.get_country_pop(code);
    }

    @GetMapping("/total_area/{code}")
    public double totalArea(@PathVariable String code) {
        return geo.get_country_total_area(code);
    }

    @GetMapping("/capital/{code}")
    public String capital(@PathVariable String code) {
        return geo.country_capital_name(code);
    }

    @GetMapping("/political_system/{code}")
    public String politicalSystem(@PathVariable String code) {
        return geo.get_country_political_system(code);
    }
}
