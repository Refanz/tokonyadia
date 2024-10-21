package com.refanzzzz.tokonyadia.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentStatus {
    SETTLEMENT("settlement"),
    PENDING("pending"),
    DENY("deny"),
    CANCEL("cancel"),
    EXPIRE("expire");

    private final String description;

    public static PaymentStatus getPaymentStatusByDesc(String desc) {
        for (PaymentStatus status : values()) {
            if (status.description.equalsIgnoreCase(desc)) return status;
        }

        return null;
    }
}
