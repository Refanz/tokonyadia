package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.cart.CartDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.cart.CartRequest;
import com.refanzzzz.tokonyadia.dto.request.cart.CartSearchRequest;
import com.refanzzzz.tokonyadia.dto.request.cart.CartUpdateRequest;
import com.refanzzzz.tokonyadia.dto.response.cart.CartResponse;
import com.refanzzzz.tokonyadia.dto.response.cart.CartUpdateResponse;
import com.refanzzzz.tokonyadia.entity.Cart;
import org.springframework.data.domain.Page;

public interface CartService {
    CartResponse createCart(CartRequest request);

    Page<CartResponse> getAllCartByCustomer(CartSearchRequest request);

    Cart getCartByStoreAndCustomer(String storeId, String customerId);

    CartResponse addCartItem(String cartId, CartDetailRequest request);

    Cart getOne(String id);

    CartUpdateResponse updateCart(String id, CartUpdateRequest request);

    void deleteCartItem(String cartId, String cartDetailId);
}
