package com.example.bulletinboard.security;

import com.example.bulletinboard.entity.User;
import com.example.bulletinboard.repository.UserRepo;
import com.example.bulletinboard.service.AuthUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Primary
public class AuthUserManager implements UserDetailsManager {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserMapper authUserMapper;
    private final AuthUser authUser;

    @Override
    public void createUser(UserDetails user) {

    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        if (passwordEncoder.matches(oldPassword, authUser.getPassword())) {
            User user = userRepo.findByEmail(authUser.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
        } else {
            throw new UnsupportedOperationException("Неправильно введен пароль");
        }
    }

    @Override
    public boolean userExists(String username) {
        return userRepo.existsByEmail(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Такого пользователя не существует"));
        AuthUserDto authUserDto = authUserMapper.toAuthUser(user);
        authUser.setUser(authUserDto);
        return authUser;
    }
}
