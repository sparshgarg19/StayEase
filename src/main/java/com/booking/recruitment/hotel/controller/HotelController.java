package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/hotel")
public class HotelController {
  private final HotelService hotelService;

  @Autowired
  public HotelController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping(value = "/{hotelId}")
  @ResponseStatus(HttpStatus.OK)
  public Hotel getHotelById(@PathVariable Long hotelId) {
      return hotelService.getHotelById(hotelId);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Hotel> getAllHotels() {
    return hotelService.getAllHotels();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Hotel createHotel(@RequestBody Hotel hotel) {
    return hotelService.createNewHotel(hotel);
  }


  @DeleteMapping("/{hotelId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteHotel(@PathVariable Long hotelId) {
      hotelService.deleteHotel(hotelId);
  }
  
}
