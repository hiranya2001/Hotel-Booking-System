package com.example.demo.hotel.service.interfac;

import com.example.demo.hotel.dto.Response;
import com.example.demo.hotel.entity.Booking;

public interface IBookingService {

    Response saveBooking(String rooId, String userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(String bookingId);
}
