package com.selflearn.tree.service;

import java.text.ParseException;
import java.util.List;

import com.selflearn.tree.datamapper.UserDTO;
import com.selflearn.tree.datamapper.UserValidate;
import com.selflearn.tree.model.UserModel;


public interface UserService {
	
	UserModel createUser(UserDTO customer) throws ParseException;
	List<UserModel> getAllUser();
	UserModel updateUserById(long id, UserDTO userDto);
	void deleteUserById(long id);
	List<UserModel> getUserBy(String name, Integer roleId);
	UserModel updatePasswordUserById(long id, UserValidate validate);
}
