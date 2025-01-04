package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.exception.BadRequestException;
import com.booking.recruitment.hotel.exception.ElementNotFoundException;
import com.booking.recruitment.hotel.model.City;
import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.CityRepository;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Comparator;

@Service
class DefaultHotelService implements HotelService {
  private final HotelRepository hotelRepository;
  private final CityRepository cityRepository;

  @Autowired
  DefaultHotelService(HotelRepository hotelRepository, CityRepository cityRepository) {
    this.hotelRepository = hotelRepository;
    this.cityRepository = cityRepository;
  }

  @Override
  public List<Hotel> getAllHotels() {
    // Donot Show IsDeleted = true hotels to customer
    return hotelRepository.findAll().stream()
        .filter((hotel) -> !hotel.isDeleted())
        .collect(Collectors.toList());
  }

  @Override
  public List<Hotel> getHotelsByCity(Long cityId) {
    return hotelRepository.findAll().stream()
        .filter((hotel) -> cityId.equals(hotel.getCity().getId()))
        .collect(Collectors.toList());
  }

  @Override
  public Hotel createNewHotel(Hotel hotel) {
    if (hotel.getId() != null) {
      throw new BadRequestException("The ID must not be provided when creating a new Hotel");
    }

    return hotelRepository.save(hotel);
  }

  @Override
  public Hotel getHotelById(Long hotelId) {
    return hotelRepository.findById(hotelId)
        .filter((hotel) -> !hotel.isDeleted())
        .orElse(null);
  }

  public void deleteHotel(Long hotelId) {
    Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ElementNotFoundException("Hotel not found"));
    hotel.setDeleted(true);
    hotelRepository.save(hotel);
  }

  public List<Hotel> getNearestHotelsToCity(Long cityId, String sortBy) {
    City city = cityRepository.findById(cityId).orElseThrow(() -> new ElementNotFoundException("City not found"));
    Stream<Hotel> filteredHotels =  hotelRepository.findAll().stream()
        .filter((hotel) -> cityId.equals(hotel.getCity().getId()) && !hotel.isDeleted());

    if(sortBy.equals("distance")) {
      filteredHotels = filteredHotels.sorted(Comparator.comparingDouble(
        hotel -> haversine(city.getCityCentreLatitude(), city.getCityCentreLongitude(), hotel.getLatitude(), hotel.getLongitude())));
    }

    return filteredHotels.limit(3)
        .collect(Collectors.toList());
  }

  private double haversine(double lat1, double lon1, double lat2, double lon2) {
    final int R = 6371; // Radius of the earth in km
    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // Distance in km
  }
}
