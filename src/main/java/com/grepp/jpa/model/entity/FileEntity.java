package com.grepp.jpa.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FILE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ChatEntity chat;

    @Column(name = "ORIGINAL_NAME", nullable = false)
    private String originalName;

    @Column(name = "SAVED_PATH", nullable = false)
    private String savedPath;

    public void updateChat(ChatEntity chat) {
        this.chat = chat;
    }
}
