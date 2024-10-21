package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.FileType;
import com.refanzzzz.tokonyadia.dto.response.file.FileInfo;
import com.refanzzzz.tokonyadia.service.FileStorageService;
import com.refanzzzz.tokonyadia.util.ImageUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final Path rootPathDirectory;
    private final ImageUtil imageUtil;

    @Autowired
    public FileStorageServiceImpl(
            @Value("${tokonyadia.root-path-directory}") String pathDirectory,
            ImageUtil imageUtil) {
        this.rootPathDirectory = Paths.get(pathDirectory).normalize();
        this.imageUtil = imageUtil;
    }

    @PostConstruct
    public void initDirectory() {
        if (!Files.exists(rootPathDirectory)) {
            try {
                Files.createDirectories(rootPathDirectory);
                Files.setPosixFilePermissions(rootPathDirectory, PosixFilePermissions.fromString("rwxr-xr-x"));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while init directory!");
            }
        }
    }

    @Override
    public FileInfo storeFile(FileType fileType, String prefixDirectory, MultipartFile multipartFile, List<String> contentType) {
        try {
            imageUtil.validateImage(multipartFile);

            String prefix = fileType.equals(FileType.FILE) ? "files" : "images";
            String filename = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

            Path dirPath = rootPathDirectory.resolve(prefix).normalize();
            Path newDirPath = dirPath.resolve(prefixDirectory).normalize();

            if (!Files.exists(newDirPath))
                Files.createDirectories(newDirPath);

            Path filePath = newDirPath.resolve(filename).normalize();
            Files.copy(multipartFile.getInputStream(), filePath);
            Files.setPosixFilePermissions(filePath, PosixFilePermissions.fromString("rw-r--r--"));

            Path savePath = Paths.get(prefix).resolve(prefixDirectory).resolve(filename).normalize();

            return FileInfo.builder()
                    .filename(filename)
                    .path(savePath.toString())
                    .build();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while save image!");
        }
    }

    @Override
    public Resource readFile(String path) {
        try {
            Path filePath = rootPathDirectory.resolve(path);
            if (!Files.exists(filePath)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found!");

            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteFile(String path) {
        try {
            Path filePath = rootPathDirectory.resolve(path);
            if (!Files.exists(filePath)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found!");

            Files.delete(filePath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
