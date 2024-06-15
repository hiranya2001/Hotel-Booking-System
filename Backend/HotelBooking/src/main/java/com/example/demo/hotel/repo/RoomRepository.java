package com.example.demo.hotel.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.hotel.entity.Room;

public interface RoomRepository extends JpaRepository<Room, String> {

	 @Query("SELECT DISTINCT r.roomType FROM Room r")
	    List<String> findDistinctRoomType();

	    @Query("SELECT r FROM Room r LEFT JOIN r.bookings b WHERE b IS NULL")
	    List<Room> findAllAvailableRooms();

	    
	    
	    List<Room> findByRoomTypeLikeAndIdNotIn(String roomType, List<String> ids);

//		List<Room> findByRoomTypeLikeAndIdNotIn(String roomType, List<String> bookedRoomsId);

	   // List<Room> findByRoomTypeLikeAndIdNotIn(String roomType, List<String> bookedRoomsId);

		//List<Room> findByRoomTypeLikeAndIdNotIn(String roomType, List<String> bookedRoomsId);

		//List<Room> findByRoomTypeLikeAndIdNotIn(String roomType, List<String> bookedRoomsId);
}