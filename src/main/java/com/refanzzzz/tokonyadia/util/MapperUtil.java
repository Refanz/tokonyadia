package com.refanzzzz.tokonyadia.util;

import com.refanzzzz.tokonyadia.dto.response.*;
import com.refanzzzz.tokonyadia.dto.response.auth.UserAccountResponse;
import com.refanzzzz.tokonyadia.dto.response.cart.CartDetailResponse;
import com.refanzzzz.tokonyadia.dto.response.cart.CartResponse;
import com.refanzzzz.tokonyadia.dto.response.cart.CartUpdateResponse;
import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionDetailResponse;
import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionResponse;
import com.refanzzzz.tokonyadia.entity.*;

import java.util.List;

public class MapperUtil {

    public static CartResponse toCartResponse(Cart cart) {
        List<CartDetailResponse> cartDetails = cart.getCartDetails().stream().map(MapperUtil::toCardDetailResponse).toList();

        return CartResponse.builder()
                .id(cart.getId())
                .customerName(cart.getCustomer().getName())
                .storeName(cart.getStore().getName())
                .cartDetails(cartDetails)
                .build();
    }

    public static CartDetailResponse toCardDetailResponse(CartDetail cartDetail) {
        return CartDetailResponse.builder()
                .id(cartDetail.getId())
                .productName(cartDetail.getProduct().getName())
                .price(cartDetail.getPrice())
                .qty(cartDetail.getQty())
                .build();
    }

    public static CartUpdateResponse toCartUpdateResponse(Cart cart) {
        return CartUpdateResponse.builder()
                .cartId(cart.getId())
                .build();
    }


    public static CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .userid(customer.getUserAccount().getId())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }


    public static MerchantResponse toMerchantResponse(Merchant merchant) {
        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .email(merchant.getEmail())
                .phoneNumber(merchant.getPhoneNumber())
                .userId(merchant.getUserAccount().getId())
                .build();
    }


    public static ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .storeId(product.getStore().getId())
                .build();
    }


    public static StoreResponse toStoreResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .noSiup(store.getNoSiup())
                .phoneNumber(store.getPhoneNumber())
                .build();
    }

    public static StoreResponse toStoreProductResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .products(store.getProductList().stream().map(product -> ProductResponse.builder()
                        .name(product.getName())
                        .build()).toList())
                .noSiup(store.getNoSiup())
                .phoneNumber(store.getPhoneNumber())
                .build();
    }


    public static TransactionDetailResponse toTransactionDetailResponse(TransactionDetail transactionDetail) {
        return TransactionDetailResponse
                .builder()
                .id(transactionDetail.getId())
                .price(transactionDetail.getPrice())
                .qty(transactionDetail.getQty())
                .transactionId(transactionDetail.getTransaction().getId())
                .productId(transactionDetail.getProduct().getId())
                .build();
    }

    public static TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transactionDate(DateUtil.parseDateToString(transaction.getTransactionDate()))
                .build();
    }

    public static UserAccountResponse toUserAccountResponse(UserAccount userAccount) {
        return UserAccountResponse.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .role(userAccount.getRole().getDescription())
                .build();
    }

    public static PaymentResponse toPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .transactionId(payment.getTransaction().getId())
                .paymentStatus(payment.getPaymentStatus().name())
                .amount(payment.getAmount())
                .redirectUrl(payment.getRedirectUrl())
                .tokenSnap(payment.getTokenSnap())
                .build();
    }
}
