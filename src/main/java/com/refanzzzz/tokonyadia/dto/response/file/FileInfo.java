package com.refanzzzz.tokonyadia.dto.response.file;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FileInfo {
    private String filename;
    private String path;
}
