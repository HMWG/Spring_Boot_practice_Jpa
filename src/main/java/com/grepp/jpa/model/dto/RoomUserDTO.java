package com.grepp.jpa.model.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomUserDTO {
    private Long userId;
    private Long roomId;
}
