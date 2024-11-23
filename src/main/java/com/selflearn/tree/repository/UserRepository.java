package com.selflearn.tree.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.selflearn.tree.model.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

    @Query("select u from UserModel u where u.username = :userName")
    Optional<UserModel> findByUserName(@Param("userName") String userName);
}
