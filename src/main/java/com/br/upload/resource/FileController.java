package com.br.upload.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.upload.record.FileRecord;
import com.br.upload.service.FileStorageService;

@RestController
@RequestMapping("/api/files")
public class FileController {
	
	private FileStorageService service;
	
	public FileController(FileStorageService serviceP) {
		this.service = serviceP;
		
	}
	
	
	public ResponseEntity<FileRecord> upload(MultipartFile file){		
		return ResponseEntity.ok(service.saveFile(file));
	}
}
