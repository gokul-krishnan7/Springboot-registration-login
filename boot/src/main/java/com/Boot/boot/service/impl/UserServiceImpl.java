package com.Boot.boot.service.impl;

//import com.example.registrationlogindemo.dto.UserDto;
//import com.example.registrationlogindemo.entity.Role;
//import com.example.registrationlogindemo.entity.User;
//import com.example.registrationlogindemo.repository.RoleRepository;
//import com.example.registrationlogindemo.repository.UserRepository;
//import com.example.registrationlogindemo.service.UserService;
import com.Boot.boot.Dto.UserDto;
import com.Boot.boot.entity.Role;
import com.Boot.boot.entity.User;
import com.Boot.boot.repository.RoleRepository;
import com.Boot.boot.repository.UserRepository;
import com.Boot.boot.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());


//        user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));



        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));

        userRepository.save(user); //user is saved
    }


    public User findByEmail(String email) {  //this method retrieves user data by email
        return userRepository.findByEmail(email);
    }



    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}