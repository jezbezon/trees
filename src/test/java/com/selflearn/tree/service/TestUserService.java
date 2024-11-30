package com.selflearn.tree.service;

import com.selflearn.tree.datamapper.UserDTO;
import com.selflearn.tree.impl.UserServiceImpl;
import com.selflearn.tree.model.Role;
import com.selflearn.tree.model.UserModel;
import com.selflearn.tree.repository.RoleRepository;
import com.selflearn.tree.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TestUserService {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void getUser() throws Exception {

      Role role = Role.builder().id((short)1).name("ADMIN").build();
      when(roleRepository.findById(any())).thenReturn(Optional.ofNullable(role));

      UserModel userModel = new UserModel(1L,"bora","bora@gmail.com","Bora@123",1, role);
       when(userRepository.save(any(UserModel.class))).thenReturn(userModel);
        log.info(String.valueOf(userModel.formUserDto(userModel)));
       UserModel result = userService.createUser(userModel.formUserDto(userModel));

       assertEquals("bora",result.getUsername());
       verify(userRepository, times(1)).save(any(UserModel.class));

    }
}
