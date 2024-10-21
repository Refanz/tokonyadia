package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.response.file.FileDownloadResponse;
import com.refanzzzz.tokonyadia.service.ProductImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Constant.IMAGE_API)
@RequiredArgsConstructor
@Tag(name = "File", description = "APIs for File")
public class FileController {

    private final ProductImageService productImageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadImage(@PathVariable String id) {
        FileDownloadResponse fileDownloadResponse = productImageService.downloadImage(id);
        String headerValue = String.format("inline; filename=%s", fileDownloadResponse.getResource().getFilename());

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .contentType(MediaType.valueOf(fileDownloadResponse.getContentType()))
                .body(fileDownloadResponse.getResource());
    }
}
