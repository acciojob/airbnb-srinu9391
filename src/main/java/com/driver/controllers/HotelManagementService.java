package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class HotelManagementservice {
    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel) {
        return hotelManagementRepository.saveHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelManagementRepository.saveUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hotelManagementRepository.hotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        return hotelManagementRepository.roomBooking(booking);
    }

    public int getBookingByAPerson(Integer aadharCard) {
        return hotelManagementRepository.numberOfBookingByAPerson(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepository.updateNewFacilities(newFacilities, hotelName);
    }
}