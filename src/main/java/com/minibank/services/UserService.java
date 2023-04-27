package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.models.constants.Status;
import com.minibank.models.constants.UserRole;
import com.minibank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById (int userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
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
}
