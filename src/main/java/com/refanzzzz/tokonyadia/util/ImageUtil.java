package com.refanzzzz.tokonyadia.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class ImageUtil {

    @Value("${tokonyadia.file-max-size}")
    private Integer fileMaxSize;

    private final List<String> contentTypes = List.of("image/jpg", "image/png", "image/webp", "image/jpeg");

    public List<String> getImageContentTypes() {
        return this.contentTypes;
    }

    public void validateImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image cannot be empty!");

        if (multipartFile.getOriginalFilename() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename cnnot be empty!");

        if (multipartFile.getSize() > fileMaxSize)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size exceed limit");

        if (!this.contentTypes.contains(multipartFile.getContentType()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid extension type!");
    }
}
