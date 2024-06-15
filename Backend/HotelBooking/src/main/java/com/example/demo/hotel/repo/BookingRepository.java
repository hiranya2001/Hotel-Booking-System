package com.example.demo.hotel.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.hotel.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, String> {


    Optional<Booking> findByBookingConfirmationCode(String confirmationCode);

    //checkInDate is less than or equal to the checkOutDate and while the checkOutDate is greater than or equal to the checkInDate
    @Query("SELECT b FROM Booking b WHERE b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate")
    List<Booking> findBookingsWithinDateRange(@Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);


}