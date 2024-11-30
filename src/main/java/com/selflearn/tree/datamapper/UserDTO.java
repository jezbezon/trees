package com.selflearn.tree.datamapper;

import com.selflearn.tree.model.Role;
import com.selflearn.tree.model.UserModel;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private String username;
	private String email;
	private int sexId;
	private short roleId;
	private String password;

	@Override
	public String toString() {
		return "UserDTO{" +
				"username='" + username + '\'' +
				", email='" + email + '\'' +
				", sexId=" + sexId +
				", roleId=" + roleId +
				", password='" + password + '\'' +
				'}';
	}
}
