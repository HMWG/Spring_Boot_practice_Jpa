package com.grepp.jpa;

import com.grepp.jpa.model.dto.RoomDTO;
import com.grepp.jpa.model.entity.RoomEntity;
import com.grepp.jpa.model.entity.UserEntity;
import com.grepp.jpa.model.entity.RoomUserEntity;
import com.grepp.jpa.model.repository.RoomRepository;
import com.grepp.jpa.model.repository.UserRepository;
import com.grepp.jpa.model.repository.RoomUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j
class Day0902SpringPracticeJpaApplicationTests {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoomUserRepository roomUserRepository;


    @Test
    @DisplayName("테스트1")
    @Transactional
    void test1(){
        //given
        UserEntity user1 = userRepository.save(UserEntity.builder().username("123").password("123").build());
        UserEntity user2 = userRepository.save(UserEntity.builder().username("456").password("456").build());
        UserEntity user3 = userRepository.save(UserEntity.builder().username("789").password("789").build());
        RoomEntity room1 = roomRepository.save(RoomEntity.builder().name("Room 1").createdBy(1L).createdAt(LocalDateTime.now()).build());
        RoomEntity room2 = roomRepository.save(RoomEntity.builder().name("Room 2").createdBy(1L).createdAt(LocalDateTime.now()).build());
        RoomEntity room3 = roomRepository.save(RoomEntity.builder().name("Room 3").createdBy(1L).createdAt(LocalDateTime.now()).build());

        roomUserRepository.save(RoomUserEntity.builder().user(user1).room(room3).build());
        roomUserRepository.save(RoomUserEntity.builder().user(user1).room(room2).build());


        //when
        List<RoomUserEntity> userRoom = roomUserRepository.findByUserId(1L);
        userRoom.stream().map(userRoomEntity -> new RoomDTO(userRoomEntity.getRoom())).forEach(System.out::println);

        //then
    }

    @Test
    @DisplayName("테스트2")
    @Transactional
    void test2(){
        //given
        UserEntity user1 = userRepository.save(UserEntity.builder().username("123").password("123").build());
        UserEntity user2 = userRepository.save(UserEntity.builder().username("456").password("456").build());
        UserEntity user3 = userRepository.save(UserEntity.builder().username("789").password("789").build());

        RoomEntity room1 = roomRepository.save(RoomEntity.builder().name("Room 1").createdBy(1L).createdAt(LocalDateTime.now()).build());
        RoomEntity room2 = roomRepository.save(RoomEntity.builder().name("Room 2").createdBy(1L).createdAt(LocalDateTime.now()).build());
        RoomEntity room3 = roomRepository.save(RoomEntity.builder().name("Room 3").createdBy(1L).createdAt(LocalDateTime.now()).build());

        roomUserRepository.save(RoomUserEntity.builder().user(user1).room(room3).build());
        roomUserRepository.save(RoomUserEntity.builder().user(user1).room(room2).build());


        //when
        roomRepository.findAll().forEach(System.out::println);

        //then
    }

    @Test
    void contextLoads() {

    }

}
