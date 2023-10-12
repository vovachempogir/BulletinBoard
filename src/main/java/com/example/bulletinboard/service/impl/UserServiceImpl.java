package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.CreateOrUpdateUser;
import com.example.bulletinboard.dto.UserDto;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.UserMapper;
import com.example.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    @Override
    public UserDto create(UserDto user) {
        return userMapper.toDto(userRepo.save(userMapper.toUser(user)));
    }

    @Override
    public Optional<UserDto> getById(Integer id) {
        return Optional.ofNullable(userMapper.toDto(userRepo.findById(id).orElse(null)));
    }

    @Override
    public UserDto updateImage(Integer id) {
        return userMapper.toDto(userRepo.findById(id).orElse(null));
    }

    @Override
    public List<UserDto> getAll() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(Integer userId ,CreateOrUpdateUser user) {
        AtomicReference<Optional<CreateOrUpdateUser>> atomicReference = new AtomicReference<>();
        userRepo.findById(userId).ifPresentOrElse(foundUser -> {
            foundUser.setFirstName(user.getFirstName());
            foundUser.setLastName(user.getLastName());
            foundUser.setPhone(user.getPhone());
            atomicReference.set(Optional.of(userMapper.fromUpdateUser(userRepo.save(foundUser))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });
    }
}
