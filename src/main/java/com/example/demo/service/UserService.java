package com.example.demo.service;

import com.example.demo.entity.Department;
import com.example.demo.entity.User;
import com.example.demo.entity.dto.UserDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.DepartmentRepo;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final DepartmentRepo departmentRepo;
    private final ModelMapper modelMapper;

    public List<UserDto> getAllUsers() {
        return convertedToDto(
                userRepo.findAll()
        );
    }

    public UserDto getUserById(Long id) {
        return convertedToDto(
                userRepo.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("User with id:" + id + " not found!"))
        );
    }

    public Map<String, String> save(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepo.findByName(userDto.getName()));

        if (user.isEmpty()) {
            User userToSave = modelMapper.map(userDto, User.class);
            userRepo.save(userToSave);
        } else {
            throw new RuntimeException("User with name: " + userDto.getName() + " is already exist!");
        }

        return Map.of("message", "User successfully saved!");
    }

    public Map<String, String> remove(Long id) {
        Optional<User> user = userRepo.findById(id);

        if (user.isPresent()) {
            userRepo.delete(user.get());
        } else {
            throw new ResourceNotFoundException("User with id:" + id + " not found!");
        }
        return Map.of("message", "User successfully removed!");
    }

    public UserDto update(Long id, UserDto userDto) {
        Optional<User> userRepoById = userRepo.findById(id);

        User user;

        if (userRepoById.isPresent()) {
            user = userRepoById.get();
            user.setAge(userDto.getAge());
            user.setName(userDto.getName());
            userRepo.save(user);
        } else {
            throw new ResourceNotFoundException("User with id:" + id + " not found!");
        }
        return convertedToDto(user);
    }

    public Map<String, String> addUserToDepartment(Long userId, Long departmentId) {
        Set<Department> departmentSet;
        User user = userRepo.findById(userId).get();
        Department department = departmentRepo.findById(departmentId).get();
        departmentSet = user.getDepartments();
        departmentSet.add(department);
        user.setDepartments(departmentSet);
        userRepo.save(user);
        return Map.of("message", user.toString());
}


private List<UserDto> convertedToDto(List<User> employees) {
    return employees
            .stream()
            .map(data -> modelMapper.map(data, UserDto.class))
            .collect(Collectors.toList());
}

private UserDto convertedToDto(User employee) {
    return modelMapper.map(employee, UserDto.class);
}


}
