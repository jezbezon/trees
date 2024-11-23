package com.selflearn.tree.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.selflearn.tree.datamapper.UserValidate;
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
	private final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";


	@Override
	public UserModel createUser(UserDTO user)throws ParseException  {
		Role role = roleRepository.findById(user.getRoleId()).orElseThrow(()-> new CustomizeException.NotFoundException("Invalid Role","02"));
		if(!Pattern.compile(regex).matcher(user.getPassword()).matches()) {
			throw new CustomizeException.BadRequestException("Password should be at least 8 characters 1 lowercase " +
					"1 uppercase and one special character ","01");
		}
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
	public List<UserModel> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public UserModel updateUserById(long id, UserDTO userDto) {
		UserModel user = userRepository.findById(id).orElseThrow();
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		log.info("Customer updated: " + user);
		return userRepository.save(user);
	}

	@Override
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<UserModel> getByUserBy(String name, Integer roleId) {
		if(name != null && roleId !=null){
			userRepository.findByUserNameWithRole(name,roleId);
		} else if (name!=null || roleId!=null) {
			userRepository.findByUserNameOrRole(name,roleId);
		}
		return userRepository.findAll();
	}

	@Override
	public UserModel updatePasswordUserById(long id, UserValidate validate) {
		return userRepository.findById(id).map(user -> {
			if(PasswordUtils.isMatch(validate.getOldPassword() , user.getPassword())){
				if(validate.getNewPassword().equals(validate.getConfirmPassword())){
					if(Pattern.compile(regex).matcher(validate.getNewPassword()).matches()) {
						user.setPassword(PasswordUtils.encodedPassword(validate.getNewPassword()));
					}else{
						throw new CustomizeException.BadRequestException("Password should be at least 8 characters 1 lowercase " +
								"1 uppercase and one special character","01");
					}
				}else {
					throw new CustomizeException.BadRequestException("Confirm password the match!","02");
				}
			}
			log.info("Customer updated: " + user);
			return userRepository.save(user);
		}).orElseThrow(()-> new CustomizeException.NotFoundException("User not found!"));
	}


}
