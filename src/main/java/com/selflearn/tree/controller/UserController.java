package com.selflearn.tree.controller;

import java.text.ParseException;
import java.util.List;

import com.selflearn.tree.cacheConfig.CacheConfig;
import com.selflearn.tree.datamapper.UserValidate;
import com.selflearn.tree.resposeClass.ResponseClass;
import jakarta.servlet.Filter;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.selflearn.tree.datamapper.UserDTO;
import com.selflearn.tree.model.UserModel;
import com.selflearn.tree.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

	/*i use ResponseEntity before i built ResponseClass
		the reason i keep it just to understand both
	*/
	private final UserService userService;

	@PostMapping("/create")
	public ResponseEntity<UserModel> createUser(@RequestBody UserDTO userdto) throws ParseException {
        return  ResponseEntity.ok(userService.createUser(userdto));
    }

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseClass getUser() {
		return new ResponseClass("00","fetch success!",userService.getAllUser());
	}

	@GetMapping("user-by")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseClass getUserBy(@RequestParam(value = "name", required = false) String name
			, @RequestParam(value = "roleId", required = false) Integer roleId) {
		return new ResponseClass("00","fetch success!",userService.getUserBy(name,roleId));
	}

	@PutMapping("{id}")
    public ResponseEntity<UserModel> updateUserById(@PathVariable long id, @RequestBody UserDTO userDTO) {
		return  ResponseEntity.ok(userService.updateUserById(id, userDTO));
    }

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable long id) {
		userService.deleteUserById(id);
		return  ResponseEntity.ok("success delete customer id:"+id);
	}

	@PatchMapping("update-password/{id}")
	public ResponseEntity<UserModel> updatePasswordById(@PathVariable(value = "id") long id, @RequestBody  UserValidate validate) {
		return  ResponseEntity.ok().headers(CacheConfig.createNoCacheHeaders()).body(userService.updatePasswordUserById(id,validate));
	}
	

}
