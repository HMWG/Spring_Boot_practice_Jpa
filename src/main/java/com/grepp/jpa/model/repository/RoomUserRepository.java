package com.grepp.jpa.model.repository;


import com.grepp.jpa.model.entity.RoomUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUserEntity, Long> {
    List<RoomUserEntity> findByUserId(Long userId);
    int countByUserId(Long userId);
    RoomUserEntity findByUserIdAndRoomId(Long userId, Long roomId);
    void deleteByRoomId(Long roomId);

//    int insert(int userNo, int chatRoomNo);
//    UserRoomDTO findByUserNoAndChatRoomNo(int userNo, int chatRoomNo);
}
