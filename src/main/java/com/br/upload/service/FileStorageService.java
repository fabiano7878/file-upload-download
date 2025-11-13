package com.br.upload.service;

import com.br.upload.model.FileEntity;
import com.br.upload.record.FileRecord;

public class FileStorageService {

    public FileRecord toRecord(FileEntity entity) {
        String url = "/api/files/" + entity.getId(); // URL de download
        return new FileRecord(
                entity.getId(),
                entity.getFileName(),
                entity.getContentType(),
                entity.getSize(),
                url
        );
    }	
}
