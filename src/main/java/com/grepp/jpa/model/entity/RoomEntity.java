package com.grepp.jpa.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROOM")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private Long id;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<RoomUserEntity> roomUser = new ArrayList<>();

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "createdAt = " + createdAt + ", " +
                "createdBy = " + createdBy + ")";
    }
}
