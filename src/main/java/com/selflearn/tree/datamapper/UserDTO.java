package com.selflearn.tree.datamapper;

import com.selflearn.tree.model.Role;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
	
	private String username;
	private String email;
	private int sexId;
	private short roleId;
	@Size(min = 6 , max = 16)
	private String password;

}
