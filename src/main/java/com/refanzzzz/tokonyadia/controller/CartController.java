package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.CartDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.CartRequest;
import com.refanzzzz.tokonyadia.dto.response.CartResponse;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.service.CartService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constant.CART_API)
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<CommonResponse<CartResponse>> createCart(@RequestBody CartRequest request) {
        CartResponse cartResponse = cartService.createCart(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, "", cartResponse);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/{id}/items")
    public ResponseEntity<CommonResponse<CartResponse>> addCartItem(@PathVariable String id, @RequestBody CartDetailRequest request) {
        CartResponse cartResponse = cartService.addCartItem(request);
        return ResponseUtil.createResponse(HttpStatus.OK, "", cartResponse);
    }
}
