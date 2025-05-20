package com.educandoweb.course.entities.enums;

public enum Roles {
    ADMIN("admin"),
    USER("user");

    private String role;

    Roles(String user) {
        this.role = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
