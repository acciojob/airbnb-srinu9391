package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {
    Map<String, Hotel> hotelDb;
    Map<Integer, User> userDb;
    Map<String, Booking> bookingDb;
    Map<Integer, Integer> userBooking;

    public HotelManagementRepository() {
        this.hotelDb = new HashMap<String, Hotel>();
        this.userDb = new HashMap<Integer, User>();
        this.bookingDb = new HashMap<String, Booking>();
        this.userBooking = new HashMap<Integer, Integer>();
    }

    public String saveHotel(Hotel hotel) {
        if (hotel.getHotelName() == null || hotel == null) return "FAILURE";
        if (hotelDb.containsKey(hotel.getHotelName())) return "FAILURE";
        hotelDb.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer saveUser(User user) {
        if (userDb.containsKey(user.getaadharCardNo())) return 0;
        userDb.put(user.getaadharCardNo(), user);
        return user.getaadharCardNo();
    }

    public String hotelWithMostFacilities() {
        List<String> hotelNames = new ArrayList<>();
        int max = 1;
        if (hotelDb.size() == 0) return "";
        for (Hotel hotel : hotelDb.values()){
            if (hotel.getFacilities().size() >= max){
                max = hotel.getFacilities().size();
            }
        }
        for (Hotel hotel : hotelDb.values()) {
            if (hotel.getFacilities().size() == max){
                hotelNames.add(hotel.getHotelName());
            }
        }
        if (hotelNames.size() == 0) return "";
        if (hotelNames.size() == 1) return hotelNames.get(0);
        for(int i=0;i<hotelNames.size()-1;i++){
            for(int j=i+1;j<hotelNames.size();j++){
                if(hotelNames.get(i).compareTo(hotelNames.get(j))>0){
                    String temp = hotelNames.get(i);
                    hotelNames.add(i,hotelNames.get(j));
                    hotelNames.add(j,temp);
                }
            }
        }
        return hotelNames.get(0);
    }

    public int roomBooking(Booking booking) {
        Hotel hotel = hotelDb.get(booking.getHotelName());
        if (hotel.getAvailableRooms() < booking.getNoOfRooms()) return -1;
        //Generating random bookingId with UUID
        String bookingId = UUID.randomUUID().toString();
        int amount = booking.getNoOfRooms()*hotel.getPricePerNight();
        booking.setAmountToBePaid(amount);
        booking.setBookingId(bookingId);
        bookingDb.put(bookingId, booking);
        userBooking.put(booking.getBookingAadharCard(), userBooking.getOrDefault(booking.getBookingAadharCard(), 0)+1);
        return amount;
    }

    public int numberOfBookingByAPerson(Integer aadharCard) {
        for(int userAadhar : userBooking.keySet()){
            if (userAadhar == aadharCard){
                return userBooking.get(userAadhar);
            }
        }
        return 0;
    }

    public Hotel updateNewFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelDb.get(hotelName);
        for(Facility facility : newFacilities){
            if (hotel.getFacilities() == null){
                List<Facility> facilities = new ArrayList<>();
                hotel.setFacilities(facilities);
            } else if (!hotel.getFacilities().contains(facility)) {
                hotel.getFacilities().add(facility);
            }
        }
        return hotel;
    }
}