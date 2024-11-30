package com.selflearn.tree.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Table(name = "role")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(name = "name")
    private String name;

//    @OneToMany(mappedBy = "role")
//    private UserModel userModel;
}
