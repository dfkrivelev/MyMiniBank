package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.models.constants.Status;
import com.minibank.models.constants.UserRole;
import com.minibank.repositories.AccountRepository;
import com.minibank.repositories.UserRepository;
import com.minibank.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }



    @Transactional
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
        Account newAccount = createAuto(newUser);
        addUserAccounts(newUser, newAccount);
        return userRepository.save(newUser);
    }

    @Transactional
    public Account createAuto (User user) {
        Account newAccount;
        Long randomAccNumber;
        do{
            randomAccNumber = (long)(Math.random() * (99999999 - 10000000) + 10000000);
        }while (uniqueNumber(randomAccNumber));

        newAccount = new Account(randomAccNumber, user);
        newAccount.setBalance(0.0);
        newAccount.setDateTime(OffsetDateTime.now());
        newAccount.setStatus(Status.BLOCK);

        return newAccount;
    }

    private boolean uniqueNumber(Long randomAccNumber) {
        List<Account> accounts = accountRepository.findAll();
        for(Account account : accounts) {
            if(account.getAccountNumber().equals(randomAccNumber)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public User createAdmin(User user) {
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getCountry(),
                user.getPhoneNumber(), user.getEmail(), user.getPassword());

        newUser.setRole(UserRole.ADMIN);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }

    @Transactional
    public User setPassword(User user, String oldPassword, String newPassword) {
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            throw new IllegalArgumentException("incorrect password");
        }
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

    public User getAuthUser() {
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof AnonymousAuthenticationToken)){
            SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
            user = securityUser.getUser();
        }

        return user;
    }

    public List<Account> findAllAccounts(User user) {
        return user.getAccounts();
    }
    public Optional<User> findByEmail (String email) {
        return userRepository.findByEmail(email);
    }

    public List<Account> getActiveAccount(User user){
        List<Account> activeAccount = new ArrayList<>();

        for(Account account : user.getAccounts()) {
            if(!account.getStatus().equals(Status.BLOCK)) {
                activeAccount.add(account);
            }
        }
        return activeAccount;
    }
}
