package com.selflearn.tree.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearn.tree.datamapper.UserDTO;
import com.selflearn.tree.datamapper.UserValidate;
import com.selflearn.tree.model.Role;
import com.selflearn.tree.model.UserModel;
import com.selflearn.tree.resposeClass.ResponseClass;
import com.selflearn.tree.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.text.ParseException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestUserController {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        /*
            + if we use @ExtendWith(MockitoExtension.class) we dont need to use MockitoAnnotations.openMocks(this); it cause a problem if use both
            + It eliminates the need to manually call MockitoAnnotations.openMocks(this) because the MockitoExtension takes
                care of initializing all @Mock, @Spy, and @InjectMocks annotations.
         */
//        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    private final List<UserModel> userModel = List.of(new UserModel("bora","bora@gmail.com","Bora@123",1,new Role((short) 1,"ADMIN")));
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createUser() throws ParseException {

        UserModel userModel = new UserModel();
        userModel.setUsername("bora");
        userModel.setEmail("bora@gmail.com");
        userModel.setSex(1);
        userModel.setRole(new Role((short) 1,"admin"));
        when(userService.createUser(any(UserDTO.class))).thenReturn(userModel);

        userController.createUser(userModel.formUserDto(userModel));
    }

    @Test
    void getUser(){

        when(userService.getAllUser()).thenReturn(userModel);
        userController.getAllUser();

    }

    @Test
    void getUserBy() throws Exception {

        when(userService.getUserBy("bora",1)).thenReturn(userModel);
        userController.getUserBy("bora",1);

        String result = objectMapper.writeValueAsString(new ResponseClass("00", "fetch success!", userModel));
        mockMvc.perform(get("/api/user/user-by")
                        .param("name", "bora")
                        .param("roleId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void updateUserById() throws Exception{
        UserModel model = new UserModel("bora","bora@gmail.com",1,new Role((short) 1,"ADMIN"));
        when(userService.updateUserById(eq(1L),any(UserDTO.class))).thenReturn(model);

        String expect = objectMapper.writeValueAsString(model);
        String result = objectMapper.writeValueAsString(model.formUserDto(model));

        mockMvc.perform(put("/api/user/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(result))
                .andExpect(status().isOk())
                .andExpect(content().json(expect));
    }

    @Test
    void deleteById() throws Exception{
        doNothing().when(userService).deleteUserById(1);
        mockMvc.perform(delete("/api/user/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().string("success delete customer id:"+1));
    }

    @Test
    void updatePasswordUserById() throws Exception{
        UserModel model = new UserModel("bora","bora@gmail.com",1,new Role((short) 1,"ADMIN"));
        when(userService.updatePasswordUserById(eq(1L),any(UserValidate.class))).thenReturn(model);

        String expect = objectMapper.writeValueAsString(model);
        String result = objectMapper.writeValueAsString(model.formUserDto(model));

        mockMvc.perform(patch("/api/user/update-password/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(result))
                .andExpect(status().isOk())
                .andExpect(content().json(expect));
    }




}
