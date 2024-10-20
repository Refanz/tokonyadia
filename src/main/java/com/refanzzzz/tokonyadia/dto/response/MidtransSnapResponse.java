package com.refanzzzz.tokonyadia.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MidtransSnapResponse {
    @JsonProperty(value = "token")
    private String token;

    @JsonProperty(value = "redirect_url")
    private String redirectUrl;
}
