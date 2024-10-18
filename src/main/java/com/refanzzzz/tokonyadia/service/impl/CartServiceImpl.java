package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.CartDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.CartRequest;
import com.refanzzzz.tokonyadia.dto.response.CartResponse;
import com.refanzzzz.tokonyadia.entity.*;
import com.refanzzzz.tokonyadia.repository.CartRepository;
import com.refanzzzz.tokonyadia.service.CartService;
import com.refanzzzz.tokonyadia.service.CustomerService;
import com.refanzzzz.tokonyadia.service.ProductService;
import com.refanzzzz.tokonyadia.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final StoreService storeService;
    private final ProductService productService;
    private final CustomerService customerService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartResponse createCart(CartRequest request) {
        Customer customer = customerService.getOne(request.getCustomerId());
        Store store = storeService.getOne(request.getStoreId());

        Cart cart = Cart.builder()
                .store(store)
                .customer(customer)
                .cartDetails(new ArrayList<>())
                .build();

        Cart savedCart = cartRepository.saveAndFlush(cart);
        return toCartResponse(savedCart);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartResponse addCartItem(CartDetailRequest request) {
        Cart cart = getCart(request.getCartId());
        Product product = productService.getOne(request.getProductId());

        CartDetail cartItem = CartDetail.builder()
                .cart(cart)
                .product(product)
                .qty(request.getQty())
                .build();

        cart.getCartDetails().add(cartItem);

        cartRepository.saveAndFlush(cart);

        return toCartResponse(cart);
    }

    @Transactional(readOnly = true)
    @Override
    public Cart getCart(String id) {
        return cartRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_CART));
    }

    private CartResponse toCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .customerId(cart.getCustomer().getId())
                .cartDetails((cart.getCartDetails().isEmpty()) ? new ArrayList<>() : cart.getCartDetails())
                .storeId(cart.getStore().getId())
                .build();
    }
}
