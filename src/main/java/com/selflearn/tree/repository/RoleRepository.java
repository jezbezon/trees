package com.selflearn.tree.repository;

import com.selflearn.tree.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Short> {
}
