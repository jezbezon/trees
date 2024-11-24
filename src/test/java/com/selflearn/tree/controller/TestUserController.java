package com.selflearn.tree.controller;

import com.selflearn.tree.controller.UserController;
import com.selflearn.tree.datamapper.UserDTO;
import com.selflearn.tree.model.Role;
import com.selflearn.tree.model.UserModel;
import com.selflearn.tree.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestUserController {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    void createUser() throws ParseException {

        UserModel userModel = new UserModel();
        userModel.setUsername("bora");
        userModel.setEmail("bora@gmail.com");
        userModel.setSex(1);
        userModel.setRole(new Role((short) 1,"admin"));
        when(userService.createUser(any(UserDTO.class))).thenReturn(userModel);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("bora");
        userDTO.setEmail("bora@gmail.com");
        userDTO.setSexId(1);
        userDTO.setRoleId((short) 1);
        userDTO.setPassword("Bora@123");
        userController.createUser(userDTO);

    }
}
