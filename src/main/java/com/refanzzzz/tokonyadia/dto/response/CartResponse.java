package com.refanzzzz.tokonyadia.dto.response;

import com.refanzzzz.tokonyadia.entity.CartDetail;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartResponse {
    private String id;
    private String customerId;
    private String storeId;
    private List<CartDetail> cartDetails;
}
