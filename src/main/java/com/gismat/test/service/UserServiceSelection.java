package com.gismat.test.service;

import co.qyef.starter.firebase.repository.AuthorityRepository;
import co.qyef.starter.firebase.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UserServiceSelection {

    private final Logger log = LoggerFactory.getLogger(UserServiceSelection.class);

    private static final String USERS_CACHE = "users";
    public final static String NAME = "UserService";

    private  UserRepository userRepository;

    private  PasswordEncoder passwordEncoder;

    private  AuthorityRepository authorityRepository;

    private  CacheManager cacheManager;

    public UserServiceSelection(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }




    public co.qyef.starter.firebase.domain.User activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
      co.qyef.starter.firebase.domain.User object = userRepository.getUserByActivationKey(key);
       return object;
    }


}
