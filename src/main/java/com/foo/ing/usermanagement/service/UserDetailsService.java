package com.foo.ing.usermanagement.service;

import com.foo.ing.usermanagement.UserDetailsRepo;
import com.foo.ing.usermanagement.model.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsService {
    private UserDetailsRepo userDetailsRepo;

    public UserDetailsService(final UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }
    public UserDetails findUserDetailsById(final Integer userDetailsId) {
        final Optional<UserDetails> userDetailsOptional
                = userDetailsRepo.findById(userDetailsId);

        if(userDetailsOptional.isPresent()) return userDetailsOptional.get();
        return null;
    }
}
