package com.example.demo.hotel.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.hotel.dto.Response;
import com.example.demo.hotel.dto.RoomDto;
import com.example.demo.hotel.entity.Booking;
import com.example.demo.hotel.entity.Room;
import com.example.demo.hotel.exception.OurException;
import com.example.demo.hotel.repo.BookingRepository;
import com.example.demo.hotel.repo.RoomRepository;
import com.example.demo.hotel.service.AwsS3Service;
import com.example.demo.hotel.service.LocalFileService;
import com.example.demo.hotel.service.interfac.IRoomService;
import com.example.demo.hotel.utils.Utils;
import java.sql.Blob;
import javax.sql.rowset.serial.SerialBlob;
@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private LocalFileService localFileService;
    
    @Autowired
    private AwsS3Service awsS3Service;

    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        try {
        	  String imageUrl = awsS3Service.saveImageToS3(photo);
        	  Room room = new Room();
            
           
            room.setId(UUID.randomUUID().toString());
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomPrice(roomPrice);
            room.setRoomType(roomType);
            room.setRoomDescription(description);

            Room savedRoom = roomRepository.save(room);
            RoomDto roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while saving a room: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomType();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.findAll();
            List<RoomDto> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting all rooms: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteRoom(String roomId) {
        Response response = new Response();

        try {
            roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            roomRepository.deleteById(roomId);

            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting a room: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(String roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();
       
        try {
            String imageUrl = null;
            if (photo != null && !photo.isEmpty()) {
                imageUrl = localFileService.saveImage(photo);
            }
             Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));

            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (description != null) room.setRoomDescription(description);
            if (imageUrl != null) room.setRoomPhotoUrl(imageUrl);

            Room updatedRoom = roomRepository.save(room);
            RoomDto roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while updating a room: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(String roomId) {
        Response response = new Response();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            RoomDto roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting a room by id: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();

        try {
            List<Booking> bookings = bookingRepository.findBookingsWithinDateRange(checkInDate, checkOutDate);
            List<String> bookedRoomsId = bookings.stream().map(booking -> booking.getRoom().getId()).toList();

            List<Room> availableRooms = roomRepository.findByRoomTypeLikeAndIdNotIn(roomType, bookedRoomsId);
            List<RoomDto> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting available rooms by date range: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();

        try {
            List<Room> roomList = roomRepository.findAllAvailableRooms();
            List<RoomDto> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while getting all available rooms: " + e.getMessage());
        }
        return response;
    }
}
