package com.example.demo.hotel.dto;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

 
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDto {

    private String id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomDescription;
    private List<BookingDto> bookings;
    
    
    

}