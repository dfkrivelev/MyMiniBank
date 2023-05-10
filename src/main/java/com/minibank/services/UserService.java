package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.models.constants.Status;
import com.minibank.models.constants.UserRole;
import com.minibank.repositories.AccountRepository;
import com.minibank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById (Long userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    public User registration(User user) {
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getCountry(),
                user.getPhoneNumber(), user.getEmail(), user.getPassword());

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    @Transactional
    public void addUserAccounts(User user, Account account) {
        List<Account> accounts = user.getAccounts();
        accounts.add(account);
        user.setAccounts(accounts);
    }

    @Transactional
    public void changeRole(User user, UserRole role) {
        user.setRole(role);
        userRepository.save(user);
    }

    @Transactional
    public void changeStatus(User user, Status status) {
        user.setStatus(status);
        userRepository.save(user);
    }

    public List<Account> findAllAccounts(User user) {
        return user.getAccounts();
    }
    public Optional<User> findByEmail (String email) {
        return userRepository.findByEmail(email);
    }
}
