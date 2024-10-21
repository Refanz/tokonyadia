package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.constant.FileType;
import com.refanzzzz.tokonyadia.dto.response.file.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    FileInfo storeFile(FileType fileType, String prefixDirectory, MultipartFile multipartFile, List<String> contentType);

    Resource readFile(String path);

    void deleteFile(String path);
}
