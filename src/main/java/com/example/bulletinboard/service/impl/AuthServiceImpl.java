package com.example.bulletinboard.service.impl;

import com.example.bulletinboard.dto.Register;
import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.AuthService;
import com.example.bulletinboard.service.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация интерфейса AuthService, предоставляющая функциональность по  проверке входа в систему и регистрации нового пользователя.
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserRepo userRepo;

    /**
     * Проверяет, проходит ли вход в систему для заданного имени пользователя и пароля.
     *
     * @param userName имя пользователя
     * @param password пароль пользователя
     * @return true, если вход в систему прошел успешно, иначе false
     */
    @Override
    @Transactional
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    /**
     * Регистрирует нового пользователя с заданными данными регистрации.
     *
     * @param register объект Register с данными для регистрации
     * @return true, если регистрация прошла успешно, иначе false
     */
    @Override
    @Transactional
    public boolean register(Register register) {
        if (manager.userExists(register.getUsername())) {
            return false;
        }
        User user = userMapper.toUser(register);
        user.setPassword(encoder.encode(register.getPassword()));
        userRepo.save(userMapper.toUser(register));
        return true;
    }
}
