package com.br.upload.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.br.upload.exception.FileStorageException;
import com.br.upload.model.FileEntity;
import com.br.upload.record.FileRecord;
import com.br.upload.util.FileUtils;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private final Path rootPath = Paths.get("uploads");

    @PostConstruct
    public void init() {
        try {
            if (!Files.exists(rootPath)) {
                Files.createDirectories(rootPath);
            }
        } catch (IOException e) {
            throw new FileStorageException("Falha ao criar diretório de upload", e);
        }
    }

    public FileRecord saveFile(MultipartFile file) {
        try {
            String fileName = FileUtils.cleanPath(file.getOriginalFilename());
            Path target = rootPath.resolve(fileName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            FileEntity entity = new FileEntity(
                    fileName,
                    file.getContentType(),
                    file.getSize(),
                    target.toString()
            );

            // Aqui você poderia salvar no banco via repository (futuro módulo JPA)
            return new FileRecord(
                    entity.getId(),
                    entity.getFileName(),
                    entity.getContentType(),
                    entity.getSize(),
                    "/api/files/" + fileName
            );

        } catch (IOException ex) {
            throw new FileStorageException("Erro ao salvar arquivo: " + file.getOriginalFilename(), ex);
        }
    }

    public byte[] getFile(String fileName) {
        try {
            Path filePath = rootPath.resolve(fileName).normalize();
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new FileStorageException("Erro ao ler arquivo " + fileName, e);
        }
    }

    public List<FileRecord> listAllFiles() {
        try {
            return Files.list(rootPath)
                    .filter(Files::isRegularFile)
                    .map(path -> new FileRecord(
                            null,
                            path.getFileName().toString(),
                            FileUtils.detectContentType(path),
                            FileUtils.fileSize(path),
                            "/api/files/" + path.getFileName()
                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileStorageException("Erro ao listar arquivos", e);
        }
    }
}

