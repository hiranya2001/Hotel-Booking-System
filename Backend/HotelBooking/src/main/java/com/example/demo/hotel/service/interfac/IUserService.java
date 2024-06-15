package com.example.demo.hotel.service.interfac;

import com.example.demo.hotel.dto.LoginRequest;
import com.example.demo.hotel.dto.Response;
import com.example.demo.hotel.entity.User;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUSerBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(String userId);

    Response getMyInfo(String email);
}
