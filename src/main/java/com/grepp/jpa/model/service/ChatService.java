package com.grepp.jpa.model.service;


import com.grepp.jpa.model.dto.ChatDTO;
import com.grepp.jpa.model.dto.RoomDTO;
import com.grepp.jpa.model.dto.FileDTO;
import com.grepp.jpa.model.entity.ChatEntity;
import com.grepp.jpa.model.entity.FileEntity;
import com.grepp.jpa.model.entity.RoomEntity;
import com.grepp.jpa.model.entity.RoomUserEntity;
import com.grepp.jpa.model.repository.ChatRepository;
import com.grepp.jpa.model.repository.RoomRepository;
import com.grepp.jpa.model.repository.FileRepository;
import com.grepp.jpa.model.repository.RoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final FileRepository fileRepository;
    private final RoomUserRepository roomUserRepository;


    public RoomEntity viewChatRoom(Long chatRoomNo){
        return roomRepository.findById(chatRoomNo).orElseThrow(()->new RuntimeException("채팅방을 찾을 수 없습니다."));
    }

    public RoomUserEntity findRoomUser(Long roomNo, Long userNo){
        return roomUserRepository.findByUserIdAndRoomId(userNo,roomNo);
    }

    public ChatEntity chatting(ChatEntity chatEntity){
        return chatRepository.save(chatEntity);
    }

    public List<ChatDTO> chatList(Long roomId){
        List<ChatEntity> chatList = chatRepository.findByRoomUser_Room_IdOrderByCreatedAt(roomId);
        List<ChatDTO> chatDTOList = chatList.stream().map(ChatDTO::new).toList();
        chatDTOList
                .forEach(chatDTO -> chatDTO.setFileDTOList(fileRepository.findByChat_Id(chatDTO.getChatId()).stream().map(FileDTO::new).toList()));
        return chatDTOList;
    }

    public int saveFileInfos(List<FileEntity> fileEntities, Long chatNo){ // 작성된 글 하나에 파일이 여러개 첨부될 수 있음
        if(fileEntities == null || fileEntities.isEmpty()) return 0;
        for(FileEntity fileEntity : fileEntities){
            fileEntity.updateChat(chatRepository.findById(chatNo).orElseThrow(()->new RuntimeException("채팅을 찾을 수 없습니다.")));
        }
        fileRepository.saveAll(fileEntities);
        return 1;
    }

    public FileEntity getFileInfo(Long fileNo){
        // file 다운로드 카운트를 update 한다던지 뭐 부가작업 필요하면 여기서 해야 함
        return fileRepository.findById(fileNo).orElseThrow(()->new RuntimeException("파일을 찾을 수 없습니다."));
    }
}
