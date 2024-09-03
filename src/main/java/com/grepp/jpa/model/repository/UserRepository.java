package com.grepp.jpa.model.repository;


import com.grepp.jpa.model.dto.UserDTO;
import com.grepp.jpa.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
//    int addUser(UserDTO user);
//    UserDTO findByUsernameAndPassword(String username, String password);
//    List<UserDTO> findAllUsers();
}
