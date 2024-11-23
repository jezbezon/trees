package com.selflearn.tree.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.selflearn.tree.exceptions.CustomizeException;
import com.selflearn.tree.model.Role;
import com.selflearn.tree.repository.RoleRepository;
import com.selflearn.tree.securityConfiguration.PasswordUtils;
import org.springframework.stereotype.Service;

import com.selflearn.tree.datamapper.UserDTO;
import com.selflearn.tree.model.UserModel;
import com.selflearn.tree.model.Sex;
import com.selflearn.tree.repository.UserRepository;
import com.selflearn.tree.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;


	@Override
	public UserModel createCustomer(UserDTO user)throws ParseException  {
		Role role = roleRepository.findById(user.getRoleId()).orElseThrow(()-> new CustomizeException.NotFoundException("Invalid Role","00"));
		UserModel userModel = UserModel.builder()
										.username(user.getUsername())
										.email(user.getEmail())
										.password(PasswordUtils.encodedPassword(user.getPassword()))
										.sex(Sex.getByCode(user.getSexId()))
										.role(role)
										.build();
		log.info("Customer created: " + userModel);
		return userRepository.save(userModel);
	}

	@Override
	public List<UserModel> getAllCustomer() {
		return userRepository.findAll();
	}

	@Override
	public UserModel updateCustomer(long id, UserDTO userDto) {
		UserModel user = userRepository.findById(id).orElseThrow();
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		log.info("Customer updated: " + user);
		return userRepository.save(user);
	}

	@Override
	public void deleteCustomerId(long id) {
		userRepository.deleteById(id);
	}


}
