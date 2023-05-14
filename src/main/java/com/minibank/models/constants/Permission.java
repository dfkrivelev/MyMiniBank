package com.minibank.models.constants;

public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_ADMIN("user:admin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
