package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.cart.CartDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.cart.CartRequest;
import com.refanzzzz.tokonyadia.dto.request.cart.CartSearchRequest;
import com.refanzzzz.tokonyadia.dto.request.cart.CartUpdateRequest;
import com.refanzzzz.tokonyadia.dto.response.cart.CartResponse;
import com.refanzzzz.tokonyadia.dto.response.cart.CartUpdateResponse;
import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionResponse;
import com.refanzzzz.tokonyadia.entity.*;
import com.refanzzzz.tokonyadia.repository.CartRepository;
import com.refanzzzz.tokonyadia.service.CartService;
import com.refanzzzz.tokonyadia.service.CustomerService;
import com.refanzzzz.tokonyadia.service.ProductService;
import com.refanzzzz.tokonyadia.service.StoreService;
import com.refanzzzz.tokonyadia.util.MapperUtil;
import com.refanzzzz.tokonyadia.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final StoreService storeService;
    private final ProductService productService;
    private final CustomerService customerService;

    @Override
    public Page<CartResponse> getAllCartByCustomer(CartSearchRequest request) {
        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);

        Customer customer = customerService.getOne(request.getCustomerId());

        Page<Cart> cartPage = cartRepository.findCartsByCustomer(pageable, customer);

        return cartPage.map(MapperUtil::toCartResponse);
    }

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
        return MapperUtil.toCartResponse(savedCart);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartResponse addCartItem(CartDetailRequest request) {
        Cart cart = getOne(request.getCartId());
        Product product = productService.getOne(request.getProductId());

        Optional<CartDetail> existingCartDetail = cart.getCartDetails().stream()
                .filter(cartDetail -> cartDetail.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        if (existingCartDetail.isPresent()) {
            CartDetail cartDetail = existingCartDetail.get();
            cartDetail.setQty(cartDetail.getQty() + request.getQty());
            cartDetail.setPrice(product.getPrice());
        } else {
            CartDetail newCartItem = CartDetail.builder()
                    .cart(cart)
                    .price(product.getPrice())
                    .product(product)
                    .qty(request.getQty())
                    .build();

            cart.getCartDetails().add(newCartItem);
        }

        cartRepository.saveAndFlush(cart);

        return MapperUtil.toCartResponse(cart);
    }

    @Transactional(readOnly = true)
    @Override
    public Cart getOne(String id) {
        return cartRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, Constant.ERROR_GET_CART));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CartUpdateResponse updateCart(String id, CartUpdateRequest request) {
        Cart cart = getOne(id);

        CartDetail cardDetail = cart.getCartDetails().stream()
                .filter(cartDetail -> cartDetail.getId().equals(request.getCartDetailId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ""));

        cardDetail.setQty(request.getQty());

        cartRepository.saveAndFlush(cart);

        return MapperUtil.toCartUpdateResponse(cart);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse checkoutCart(Customer customer) {
        Cart cart = cartRepository.getCartByCustomer(customer);

        for (CartDetail cartDetail : cart.getCartDetails()) {
            if (cartDetail.getQty() > cartDetail.getProduct().getStock()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of products you buy is more in stock");
            }
        }

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCartItem(String cartId, String cartDetailId) {
        Cart cart = getOne(cartId);

        cart.getCartDetails().removeIf(item -> item.getId().equals(cartDetailId));

        cartRepository.saveAndFlush(cart);
    }
}
