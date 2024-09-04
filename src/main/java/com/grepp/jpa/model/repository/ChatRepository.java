package com.grepp.jpa.model.repository;


import com.grepp.jpa.model.dto.ChatDTO;
import com.grepp.jpa.model.entity.ChatEntity;
import com.grepp.jpa.model.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    List<ChatEntity> findByRoomUser_Room_IdOrderByCreatedAt(Long roomId);
    void deleteByRoomUser_Room_Id(Long roomId);

//    int insertChat(ChatDTO chatDTO);
//    List<ChatDTO> selectChatbyChatRoomId(int chatRoomNo);
}
