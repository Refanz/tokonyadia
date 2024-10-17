package com.refanzzzz.tokonyadia.constant;

public enum TransactionStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    PROCESSING("Processing"),
    COMPLETED("Completed");

    private final String name;

    TransactionStatus(String name) {
        this.name = name;
    }

    public static TransactionStatus getDescription(String description) {
        for (TransactionStatus status : values()) {
            if (status.name.equalsIgnoreCase(description)) return status;
        }

        return null;
    }

}
