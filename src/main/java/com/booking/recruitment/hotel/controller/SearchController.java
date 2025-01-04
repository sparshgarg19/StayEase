package com.booking.recruitment.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final HotelService hotelService;

    @Autowired
    public SearchController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping(value = "/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Hotel> getNearestHotelsToCity(@PathVariable Long cityId, @RequestParam(defaultValue = "distance") String sortBy) {
        return hotelService.getNearestHotelsToCity(cityId, sortBy);
    }
}
