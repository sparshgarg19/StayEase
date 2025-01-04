package com.booking.recruitment.hotel.service;

import com.booking.recruitment.hotel.model.Hotel;

import java.util.List;

public interface HotelService {
  List<Hotel> getAllHotels();

  List<Hotel> getHotelsByCity(Long cityId);

  Hotel getHotelById(Long hotelId);

  Hotel createNewHotel(Hotel hotel);

  void deleteHotel(Long hotelId);

  List<Hotel> getNearestHotelsToCity(Long cityId, String sortBy);
}
