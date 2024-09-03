package com.grepp.jpa.model.dto;

import com.grepp.jpa.model.entity.FileEntity;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {
    private Long fileId;
    private String originalName;
    private String savedPath;

    public FileEntity toFileEntity() {
        FileEntity fileEntity = new FileEntity();
        this.fileId = fileEntity.getId();
        this.originalName = fileEntity.getOriginalName();
        this.savedPath = fileEntity.getSavedPath();
        return fileEntity;
    }

    public FileDTO(FileEntity fileEntity) {
        this.fileId = fileEntity.getId();
        this.originalName = fileEntity.getOriginalName();
        this.savedPath = fileEntity.getSavedPath();
    }

}
