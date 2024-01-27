package com.example.demo.entity.dto;

import com.example.demo.entity.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class DepartmentDto {
    private Long id;
    private String name;
    private Set<UserDto> users = new HashSet<>();
}
