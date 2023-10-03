package com.Boot.boot.service;

import com.Boot.boot.Dto.UserDto;
import com.Boot.boot.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

}
