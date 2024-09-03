package com.grepp.jpa.model.dto;

import com.grepp.jpa.model.entity.ChatEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDTO {
    private Long chatId;
    private Long roomId;
    private Long userId;
    private String chatText;
    private LocalDateTime createdAt;
    private List<FileDTO> fileDTOList;

    public ChatDTO(ChatEntity chatEntity) {
        this.chatId = chatEntity.getId();
        this.roomId = chatEntity.getRoomUser().getRoom().getId();
        this.userId = chatEntity.getRoomUser().getUser().getId();
        this.chatText = chatEntity.getChatText();
        this.createdAt = chatEntity.getCreatedAt();
    }

    public void setFileDTOList(List<FileDTO> fileDTOList) {
        this.fileDTOList = fileDTOList;
    }
}
