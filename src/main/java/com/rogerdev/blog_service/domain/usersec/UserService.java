package com.rogerdev.blog_service.domain.usersec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepo;

    public UserSec saveUser (UserSec newUser) {
        return userRepo.save(newUser);
    }
    public List<UserSec> getUsers () {
        return userRepo.findAll();
    }
    public UserSec findUser (Long userId) {
        return userRepo.getReferenceById(userId);
    }
    public void deleteUser (Long userId) {
        userRepo.deleteById(userId);
    }
    public String encryptPassword (String plainPassword) {
        return new BCryptPasswordEncoder().encode(plainPassword);
    }
    public Optional<UserSec> getUserByUsername (String username) {
        return userRepo.getUserEntityByUsername(username);
    }
}
