package com.refanzzzz.tokonyadia.dto.response.file;

import lombok.*;
import org.springframework.core.io.Resource;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FileDownloadResponse {
    private Resource resource;
    private String contentType;
}
