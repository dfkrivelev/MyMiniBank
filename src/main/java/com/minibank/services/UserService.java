package com.minibank.services;

import com.minibank.models.Account;
import com.minibank.models.User;
import com.minibank.models.constants.Status;
import com.minibank.models.constants.UserRole;
import com.minibank.repositories.AccountRepository;
import com.minibank.repositories.UserRepository;
import com.minibank.security.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
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

    public User findById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    public User registration(User user) {
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getCountry(),
                user.getPhoneNumber(), user.getEmail(), user.getPassword());

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Account newAccount = createAccountAuto(newUser);
        addUserAccounts(newUser, newAccount);

        newUser = userRepository.save(newUser);

        logger.info("Created new user, userId={}", newUser.getId());
        return newUser;
    }

    @Transactional
    public Account createAccountAuto(User user) {
        Account newAccount;
        Long randomAccNumber;
        do {
            randomAccNumber = (long) (Math.random() * (99999999 - 10000000) + 10000000);
        } while (uniqueNumber(randomAccNumber));

        newAccount = new Account(randomAccNumber, user);
        newAccount.setBalance(0.0);
        newAccount.setDateTime(OffsetDateTime.now());
        newAccount.setStatus(Status.BLOCK);

        logger.info("create new Account, for userEmail={} accountNumber={}", user.getEmail(), newAccount.getAccountNumber());
        return newAccount;
    }

    private boolean uniqueNumber(Long randomAccNumber) {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(randomAccNumber)) {
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

        newUser = userRepository.save(newUser);
        logger.info("create new admin, adminId={}", newUser.getId());

        return newUser;
    }

    @Transactional
    public void setPassword(User user, String oldPassword, String newPassword) {
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else {
            logger.warn("incorrect password for userId={}", user.getId());
            throw new IllegalArgumentException("incorrect password");
        }
        user = userRepository.save(user);
        logger.info("Set password successful for userId={}", user.getId());
    }

    @Transactional
    public void addUserAccounts(User user, Account account) {
        List<Account> accounts = user.getAccounts();
        accounts.add(account);
        user.setAccounts(accounts);
        logger.info("added accountNumber={} for user userId={}", account.getAccountNumber(), user.getId());
    }

    @Transactional
    public User changeRole(User user, UserRole role) {
        user.setRole(role);
        user = userRepository.save(user);
        logger.info("change role on userRole={} for userId={}", user.getRole(), user.getId());

        return user;
    }

    @Transactional
    public User changeStatus(User user, Status status) {
        user.setStatus(status);
        user = userRepository.save(user);
        logger.info("change status on userStatus={} for userId={}", user.getStatus(), user.getId());

        return user;
    }

    public User getAuthUser() {
        User user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
            user = securityUser.getUser();

            logger.info("authentication user, userId={}", user.getId());
        }

        return user;
    }

    public List<Account> findAllAccounts(User user) {
        return user.getAccounts();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Account> getMyAccount(User user) {
        List<Account> activeAccount = new ArrayList<>();

        for (Account account : user.getAccounts()) {
            if (!account.getStatus().equals(Status.DELETE)) {
                activeAccount.add(account);
            }
        }
        return activeAccount;
    }
}
