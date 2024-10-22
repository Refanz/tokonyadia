package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.cart.CartDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.cart.CartRequest;
import com.refanzzzz.tokonyadia.dto.request.cart.CartSearchRequest;
import com.refanzzzz.tokonyadia.dto.request.cart.CartUpdateRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.cart.CartResponse;
import com.refanzzzz.tokonyadia.dto.response.cart.CartUpdateResponse;
import com.refanzzzz.tokonyadia.dto.response.cart.CartCheckoutResponse;
import com.refanzzzz.tokonyadia.service.CartService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constant.CART_API)
@RequiredArgsConstructor
@Tag(name = "Cart", description = "APIs for Cart")
public class CartController {

    private final CartService cartService;

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/{customerId}")
    public ResponseEntity<?> getAllCartByCustomer(
            @RequestParam(name = "q", required = false) String q,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @PathVariable String customerId) {

        CartSearchRequest cartSearchRequest = CartSearchRequest.builder()
                .customerId(customerId)
                .query(q)
                .size(size)
                .page(page)
                .sortBy(sortBy)
                .build();

        Page<CartResponse> cartResponses = cartService.getAllCartByCustomer(cartSearchRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "", cartResponses);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping
    public ResponseEntity<CommonResponse<CartResponse>> createCart(@RequestBody CartRequest request) {
        CartResponse cartResponse = cartService.createCart(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, "", cartResponse);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/{id}/items")
    public ResponseEntity<CommonResponse<CartResponse>> addCartItem(@PathVariable String id, @RequestBody CartDetailRequest request) {
        CartResponse cartResponse = cartService.addCartItem(id, request);
        return ResponseUtil.createResponse(HttpStatus.OK, "", cartResponse);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PutMapping("/{id}/items")
    public ResponseEntity<CommonResponse<CartUpdateResponse>> updateCartDetail(@PathVariable String id, @RequestBody CartUpdateRequest request) {
        CartUpdateResponse updatedCart = cartService.updateCart(id, request);
        return ResponseUtil.createResponse(HttpStatus.OK, "", updatedCart);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/{cartId}/items/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable String cartId, @PathVariable String cartItemId) {
        cartService.deleteCartItem(cartId, cartItemId);
        return ResponseUtil.createResponse(HttpStatus.OK, "", null);
    }

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/{id}/checkout")
    public ResponseEntity<?> checkoutCart(@PathVariable String id) {
        CartCheckoutResponse cartCheckoutResponse = cartService.checkoutCart(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "", cartCheckoutResponse);
    }
}
