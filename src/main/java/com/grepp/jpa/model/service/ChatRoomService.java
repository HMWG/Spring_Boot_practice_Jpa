package com.grepp.jpa.model.service;


import com.grepp.jpa.model.dto.RoomDTO;
import com.grepp.jpa.model.entity.RoomEntity;
import com.grepp.jpa.model.entity.RoomUserEntity;
import com.grepp.jpa.model.repository.ChatRepository;
import com.grepp.jpa.model.repository.RoomRepository;
import com.grepp.jpa.model.repository.RoomUserRepository;
import com.grepp.jpa.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private static final int COUNT_PER_PAGE = 10;

    private final RoomRepository roomRepository;
    private final RoomUserRepository userRoomRepository;
    private final RoomUserRepository roomUserRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;


    public Map<String, Object> getList(int page, Long id) throws SQLException {

        int totalCount;
        if (id == null){
            totalCount = (int) roomRepository.count();
        }
        else {
            totalCount = roomUserRepository.countByUserId(id);
        }

        int totalPageCount = totalCount / COUNT_PER_PAGE; // (ex : 27/10 = 2.7 = 2)
        if(totalCount % COUNT_PER_PAGE > 0){ // 10개씩 2페이지하고 7개의 글이 남은 상태라 페이지 하나 늘려주기
            totalPageCount++; // 총 페이지는 3개
        }

        int startPage = (page - 1)/10 * 10 + 1; // 현재 페이지가 11, 12, 13,..., 20 이었을 때, -1 해서 10~19로 바꾸고 /10*10 하면 11, 12, .., 19 다 동일하게 10으로 통일됨
        int endPage = startPage+9;
        if(totalPageCount < endPage){ // 총 페이지 수보다 범위가 넓을 때
            endPage = totalPageCount; //마지막 페이지 링크를 총 페이지 수로 줄여줍시다
        }

        int startRow = (page - 1) * COUNT_PER_PAGE; // 한 페이지당 보여질 줄의 갯수 반영해서 db에 모든 글들 다 읽어오지 않고 여기부터 끊어서 읽으라고 알려줄 수 있음
        List<RoomEntity> roomList;
        if (id == null){
            roomList = roomRepository.findAllPage(startRow, COUNT_PER_PAGE);
        }
        else {
            roomList = roomRepository.findByUserId(startRow, COUNT_PER_PAGE, id);
        }
        /////////////////////// service는 이렇게 여러가지 비즈니스 로직을 수행해서 데이터를 계산해냄
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("page",page);
        resultData.put("startPage",startPage);
        resultData.put("endPage",endPage);
        resultData.put("totalPageCount",totalPageCount);
        resultData.put("cList",roomList);

        return resultData;
    }

    @Transactional
    public int createChatRoom(RoomEntity roomEntity, Long userNo) throws SQLException {
        roomEntity.setCreatedBy(userNo);
        RoomEntity newRoom = roomRepository.save(roomEntity);
        RoomUserEntity roomUserEntity = RoomUserEntity.builder()
                .user(userRepository.findById(userNo).orElseThrow(()->new RuntimeException("사용자를 찾을 수 없습니다.")))
                .room(newRoom)
                .build();

        RoomUserEntity newRoomUser = userRoomRepository.save(roomUserEntity);

        if(newRoom.getId()!=null && newRoomUser.getId()!=null){
            return 1;
        }
        return 0;
    }

    public RoomUserEntity inviteChatRoom(Long roomNo, Long userNo){
        RoomUserEntity roomUserEntity = RoomUserEntity.builder()
                .user(userRepository.findById(userNo).orElseThrow(()->new RuntimeException("사용자를 찾을 수 없습니다.")))
                .room(roomRepository.findById(roomNo).orElseThrow(()->new RuntimeException("채팅방을 찾을 수 없습니다.")))
                .build();
        return userRoomRepository.save(roomUserEntity);
    }

    @Transactional
    public int deleteChatRoom(Long chatRoomNo, Long userNo){
        RoomEntity roomEntity = roomRepository.findById(chatRoomNo).orElseThrow(()->new RuntimeException("채팅방을 찾을 수 없습니다."));
        if (userNo.equals(roomEntity.getCreatedBy())){

            chatRepository.deleteByRoomUser_Room_Id(chatRoomNo);
            roomUserRepository.deleteByRoomId(chatRoomNo);
            roomRepository.delete(roomEntity);
            return 1;
        }
        return 0;
    }

    public int updateChatRoom(RoomEntity roomEntity, Long userNo){
        RoomEntity room = roomRepository.findById(roomEntity.getId()).orElseThrow(()->new RuntimeException("채팅방을 찾을 수 없습니다."));
        RoomEntity newRoom = RoomEntity.builder()
                .id(room.getId())
                .name(roomEntity.getName()!=null?roomEntity.getName():room.getName())
                .description(roomEntity.getDescription()!=null?roomEntity.getDescription():room.getDescription())
                .createdBy(room.getCreatedBy())
                .createdAt(room.getCreatedAt())
                .build();
        if (userNo.equals(room.getCreatedBy())){
            roomRepository.save(newRoom);
            return 1;
        }
        return 0;
    }

    public boolean checkCreatedBy(Long chatRoomNo, Long userNo){
        RoomEntity roomEntity = roomRepository.findById(chatRoomNo).orElseThrow(()->new RuntimeException("채팅방을 찾을 수 없습니다."));
        return userNo.equals(roomEntity.getCreatedBy());
    }
}
