package com.refanzzzz.tokonyadia.constant;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_ADMIN("Admin"),
    ROLE_SUPER_ADMIN("Super Admin"),
    ROLE_CUSTOMER("Customer");

    private String description;

    UserRole(String description) {
        this.description = description;
    }

    public static UserRole getDescription(String description) {
        for (UserRole role : values()) {
            if (role.description.equalsIgnoreCase(description)) return role;
        }

        return null;
    }
}
