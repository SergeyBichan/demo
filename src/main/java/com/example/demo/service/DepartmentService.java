package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.DepartmentDto;
import com.example.demo.entity.dto.UserDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DepartmentRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    private final ModelMapper modelMapper;

    public List<DepartmentDto> getAllDepartments(){
        return convertedToDto(
                departmentRepo.findAll()
        );
    }

    public DepartmentDto getDepartmentById(Long id){
        return convertedToDto(
                departmentRepo.findById(id).orElseThrow(
                        ()-> new ResourceNotFoundException("Department with id:" + id +
                " not found!"))
        );
    }

    public Map<String, String> saveDepartment(DepartmentDto departmentDto) {
        Optional<Department> department =
                Optional.ofNullable(departmentRepo.findDepartmentByName(departmentDto.getName()));

        if (department.isEmpty()) {
            Department departmentToSave = modelMapper.map(departmentDto, Department.class);
            departmentRepo.save(departmentToSave);
        } else {
            throw new RuntimeException("Department with name: " + departmentDto.getName() + " is already exist!");
        }

        return Map.of("message", "Department successfully saved!");
    }


    private List<DepartmentDto> convertedToDto(List<Department> employees) {
        return employees
                .stream()
                .map(data -> modelMapper.map(data, DepartmentDto.class))
                .collect(Collectors.toList());
    }

    private DepartmentDto convertedToDto(Department department) {
        return modelMapper.map(department, DepartmentDto.class);
    }
}
