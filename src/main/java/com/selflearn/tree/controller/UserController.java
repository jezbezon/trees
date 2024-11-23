package com.selflearn.tree.controller;

import java.text.ParseException;
import java.util.List;

import com.selflearn.tree.resposeClass.ResponseClass;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.selflearn.tree.datamapper.UserDTO;
import com.selflearn.tree.model.UserModel;
import com.selflearn.tree.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/customer")
@RequiredArgsConstructor
public class UserController {

	/*i use ResponseEntity before i built ResponseClass
		the reason i keep it just to understand both
	*/
	private final UserService userService;
	
	@PostMapping("create")
	public ResponseEntity<UserModel> createUser(@RequestBody UserDTO userdto) throws ParseException {
        return  ResponseEntity.ok(userService.createCustomer(userdto));
    }

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseClass getUser() {
		return new ResponseClass("00","fetch success!",userService.getAllCustomer());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{id}")
    public ResponseEntity<UserModel> updateUserById(@PathVariable long id, @RequestBody UserDTO customerdto) {
		return  ResponseEntity.ok(userService.updateCustomer(id, customerdto));
    }

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable long id) {
		userService.deleteCustomerId(id);
		return  ResponseEntity.ok("success delete customer id:"+id);
	}
	

}
