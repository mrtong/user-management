package com.foo.ing.usermanagement.service;

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
    private UserDetailsRepo userDetailsRepo;

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

    @Transactional(rollbackFor = UserDetailsNotFoundException.class)
    public UserDetails updateUserDetails(
            final Integer userDetailsId,
            UserDetails userDetails) {

        final UserDetails foundUserDetails = findUserDetailsById(userDetailsId);
        log.debug("UserDetails was found in updateUserDetails(...).");

        userDetails.setUserId(foundUserDetails.getUserId());

        final UserDetails updatedUserDetails = userDetailsRepo.save(userDetails);

        log.info("UserDetails was updated.");

        return updatedUserDetails;
    }
}
