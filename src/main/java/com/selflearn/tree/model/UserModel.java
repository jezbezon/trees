package com.selflearn.tree.model;


import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selflearn.tree.exceptions.CustomizeException;
import com.selflearn.tree.resposeClass.AuditModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserModel extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username", unique = true , length = 128, nullable = false )
	@NotEmpty(message = "username can't null")
	private String username;

	@Column(name = "email", unique = true , length = 256)
	private String email;

	@Column(name = "password", nullable = false )
	@JsonIgnore
	private String password;
	
	@Column(name = "sex", nullable = false)
	private int sex;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="role_id")
	private Role role;

	public String getRole(){
		return role.getName();
	}

	public String getSex(){
		return Arrays.stream(Sex.values())
				.filter(s->s.getValue()==sex)
				.findFirst()
				.map(Sex::toString)
				.orElseThrow(()-> new CustomizeException.NotFoundException("Invalid code"));
	}

}