package com.grepp.jpa.model.repository;


import com.grepp.jpa.model.entity.RoomEntity;
import com.grepp.jpa.model.entity.RoomUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    void deleteById(Long id);

    @Modifying
    @Query(value = "SELECT DISTINCT room.room_id, room.NAME, room.DESCRIPTION, room.created_at, room.created_by " +
            "from room join room_user ucr on room.room_id = ucr.room_id " +
            "where user_id = :userId " +
            "ORDER BY created_at DESC " +
            "LIMIT :sr, :cnt", nativeQuery = true)
    List<RoomEntity> findByUserId(@Param("sr") int startRow, @Param("cnt")int count, Long userId);

    @Modifying
    @Query(value = "SELECT DISTINCT room.room_id, room.NAME, room.DESCRIPTION, room.created_at, room.created_by " +
            "from room " +
            "ORDER BY created_at DESC " +
            "LIMIT :sr, :cnt", nativeQuery = true)
    List<RoomEntity> findAllPage(@Param("sr") int startRow, @Param("cnt")int count);

//    int insert(RoomDTO roomDTO) throws SQLException;
//    int update(RoomDTO roomDTO) throws SQLException;
//    int delete(int id) throws SQLException;
//    List<RoomDTO> selectByUserId(@Param("sr")int startRow, @Param("cnt")int count, @Param("id")int id) throws SQLException;
//    int selectCountByUserId(int id) throws SQLException;
//    RoomDTO selectByChatRoomId(int id) throws SQLException;
//    List<RoomDTO> selectAll(@Param("sr")int startRow, @Param("cnt")int count) throws SQLException;
//    int selectCountAll() throws SQLException;
}
