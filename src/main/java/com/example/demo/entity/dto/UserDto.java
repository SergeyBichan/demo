package com.example.demo.entity.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private Set<DepartmentDto> departments = new HashSet<>();
}
