package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String email, String password) {
        var userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            // Тут можно добавить лог, если надо:
            System.out.println("В базе пароль: '" + user.getPassword() + "'");
            System.out.println("Введён пароль: '" + password + "'");
            if (user.getPassword().equals(password)) {
                return userOpt;
            }
        }
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Spring Security ищет пользователя: " + email);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + email));
        System.out.println("Пароль из БД: " + user.getPassword());
        System.out.println("Роль из БД: " + user.getRole());
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
