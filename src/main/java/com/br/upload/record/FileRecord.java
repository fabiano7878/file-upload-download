package com.br.upload.record;

public record FileRecord(
        Long id,
        String fileName,
        String contentType,
        long size,
        String downloadUrl
) {}

