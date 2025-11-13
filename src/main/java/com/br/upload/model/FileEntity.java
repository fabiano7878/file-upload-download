package com.br.upload.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_file")
public class FileEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;
    private long size;
    private String path;

    // 🔸 Construtor padrão exigido pelo JPA
    public FileEntity() {}

    public FileEntity(String fileName, String contentType, long size, String path) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
        this.path = path;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getFileName() { return fileName; }
    public String getContentType() { return contentType; }
    public long getSize() { return size; }
    public String getPath() { return path; }

    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public void setSize(long size) { this.size = size; }
    public void setPath(String path) { this.path = path; }
}
