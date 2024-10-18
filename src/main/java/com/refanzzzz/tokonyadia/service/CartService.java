package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.CartDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.CartRequest;
import com.refanzzzz.tokonyadia.dto.response.CartResponse;
import com.refanzzzz.tokonyadia.entity.Cart;

public interface CartService {
    CartResponse createCart(CartRequest request);

    CartResponse addCartItem(CartDetailRequest request);

    Cart getCart(String id);
}
