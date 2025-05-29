package com.aston.task2.service;

import com.aston.task2.repository.UserRepository;
import com.aston.task2.dto.UserDto;
import com.aston.task2.entity.User;
import com.aston.task2.exception.UserNotFoundException;
import com.aston.task2.util.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto save(UserDto userDto) {
        User user = modelMapper.getModelMapper().map(userDto, User.class);

        User savedUser = userRepository.save(user);

        return modelMapper.getModelMapper().map(savedUser, UserDto.class);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        return modelMapper.getModelMapper().map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.getModelMapper().map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User excitingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден" + id));

        modelMapper.getModelMapper().map(userDto, excitingUser);

        User updateUser = userRepository.save(excitingUser);

        return modelMapper.getModelMapper().map(updateUser, UserDto.class);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        userRepository.delete(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        return modelMapper.getModelMapper().map(user, UserDto.class);
    }
}
