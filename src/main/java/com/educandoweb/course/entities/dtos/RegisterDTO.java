package com.educandoweb.course.entities.dtos;

import com.educandoweb.course.entities.enums.Roles;

public record RegisterDTO(String name, String email, String phone, String password, Roles role) {
}
