package com.br.upload.util;

import java.io.IOException;
import java.nio.file.*;

public class FileUtils {

    public static String cleanPath(String fileName) {
        return fileName.replace("..", "").trim();
    }

    public static String detectContentType(Path path) {
        try {
            return Files.probeContentType(path);
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }

    public static long fileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return 0;
        }
    }
}
