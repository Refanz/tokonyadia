package com.refanzzzz.tokonyadia.client;

import com.refanzzzz.tokonyadia.dto.request.Midtrans.MidtransPaymentRequest;
import com.refanzzzz.tokonyadia.dto.response.MidtransSnapResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "midtrans", url = "${midtrans.api.url}")
public interface MidtransClient {
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/snap/v1/transactions"
    )
    MidtransSnapResponse createSnapTransaction(
            @RequestBody MidtransPaymentRequest request,
            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String authorization);
}
