package com.grepp.jpa.model.dto;

import com.grepp.jpa.model.entity.RoomEntity;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class RoomDTO {
    private Long roomId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Long createdBy;

    public RoomDTO(RoomEntity roomEntity) {
        this.roomId = roomEntity.getId();
        this.name = roomEntity.getName();
        this.description = roomEntity.getDescription();
        this.createdAt = roomEntity.getCreatedAt();
        this.createdBy = roomEntity.getCreatedBy();
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public RoomEntity toEntity() {
        return RoomEntity.builder()
                .id(this.roomId)
                .name(this.name)
                .description(this.description)
                .createdAt(this.createdAt)
                .createdBy(this.createdBy).build();
    }
}
