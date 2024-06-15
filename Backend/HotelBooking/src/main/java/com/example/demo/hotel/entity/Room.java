package com.example.demo.hotel.entity;

 

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
     private String id;

    private String roomType;

    private BigDecimal roomPrice;

    private String roomPhotoUrl;

    private String roomDescription;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", roomPrice=" + roomPrice +
                ", roomPhotoUrl='" + roomPhotoUrl + '\'' +
                ", description='" + roomDescription + '\'' +
                '}';
    }
}
