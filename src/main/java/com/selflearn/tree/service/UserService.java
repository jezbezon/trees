package com.selflearn.tree.service;

import java.text.ParseException;
import java.util.List;

import com.selflearn.tree.datamapper.UserDTO;
import com.selflearn.tree.model.UserModel;


public interface UserService {
	
	UserModel createCustomer(UserDTO customer) throws ParseException;
	List<UserModel> getAllCustomer();
	UserModel updateCustomer(long id, UserDTO customerdto);
	void deleteCustomerId(long id);
}
