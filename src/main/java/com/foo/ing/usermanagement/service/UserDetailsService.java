package com.foo.ing.usermanagement.service;

import com.foo.ing.usermanagement.exception.InvalidUserDetailsException;
import com.foo.ing.usermanagement.exception.UserDetailsNotFoundException;
import com.foo.ing.usermanagement.model.UserDetails;
import com.foo.ing.usermanagement.repo.UserDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsService {
    private final UserDetailsRepo userDetailsRepo;

    public UserDetailsService(final UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    public UserDetails findUserDetailsById(final Integer userDetailsId) throws UserDetailsNotFoundException {
        final Optional<UserDetails> userDetailsOptional
                = userDetailsRepo.findById(userDetailsId);

        if (userDetailsOptional.isPresent()) {
            log.info("[{}] was found.", userDetailsId);
            return userDetailsOptional.get();
        }

        throw new UserDetailsNotFoundException("No UserDetails were found for id " + userDetailsId);
    }

    @Transactional(rollbackFor = InvalidUserDetailsException.class)
    public UserDetails updateUserDetails(
            final Integer userDetailsId,
            UserDetails userDetails) throws InvalidUserDetailsException {

        final UserDetails foundUserDetails = findUserDetailsById(userDetailsId);
        log.debug("UserDetails was found in updateUserDetails(...).");

        userDetails.setUserId(foundUserDetails.getUserId());
        try {
            //in the implementation of JpaRepository, the save method does both persist and merge functionality
            //in the definition of the POJO UserDetails, the primary key is defined as int with auto increment
            //this is the best practice for DB performance and index performance.
            //Again from the implementation of the save method, I cant see any specific exception raised,
            //thus here I just use Exception for the catch.
            final UserDetails updatedUserDetails = userDetailsRepo.save(userDetails);
            log.info("UserDetails was updated.");

            return updatedUserDetails;
        } catch (Exception e) {
            log.error("Some exceptions happened while trying to save the userdetails [{}]", userDetailsId);

            throw new InvalidUserDetailsException("Some exceptions happened while trying to save the userdetails " + userDetailsId);
        }


    }
}
