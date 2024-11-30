package com.selflearn.tree.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.selflearn.tree.model.UserModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

    @Query("select u from UserModel u where u.username = :userName")
    Optional<UserModel> findByUserName(@Param("userName") String userName);

    @Query("select u from UserModel u where u.role.id = :roleId")
    Optional<UserModel> findByRole(@Param("roleId") int roleId);

    @Query(value = "select * from users u where (:roleId IS NOT NULL AND u.role_id = :roleId) or (:userName IS NOT NULL AND u.username LIKE %:userName%)",nativeQuery = true)
    List<UserModel> findByUserNameOrRole(@Param("userName") String userName, @Param("roleId") Integer roleId);

    @Query("select u from UserModel u where u.role.id = :roleId and u.username like %:userName%")
    List<UserModel> findByUserNameWithRole(@Param("userName") String userName, @Param("roleId") int roleId);

    Optional<UserModel> findByEmail(String email);
}
