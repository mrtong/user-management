package com.foo.ing.usermanagement.controller;

import com.foo.ing.usermanagement.exception.InvalidUserDetailsException;
import com.foo.ing.usermanagement.model.UserDetails;
import com.foo.ing.usermanagement.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping(path = "/api/userdetails")
public class UserDetailsController {
    private final UserDetailsService userDetailsService;

    public UserDetailsController(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //According to REST API, query by primary key should be a path variable instead of a request param
    //@PathVariable is not meant to be validated in order to send back a readable message to the user.
    //As principle a pathVariable should never be invalid.
    @GetMapping(
            value = "{id}",
            produces = {"application/json"})
    public ResponseEntity<UserDetails> getUserDetailsById(
            final @PathVariable(name = "id") Integer userDetailsId) {
        final UserDetails userDetails = userDetailsService.findUserDetailsById(userDetailsId);
        if (userDetails == null) {
            log.error("There is no user details found for userDetails id [{}]", userDetailsId);

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "There is no user details found for userDetails id " + userDetailsId);
        }
        log.debug("UserDetails was found for id [{}]", userDetailsId);

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @PatchMapping(
            value = "{id}",
            consumes = "application/json",
            produces = {"application/json"})
    public ResponseEntity<UserDetails> updateSymbol(
            final @PathVariable(name = "id") Integer userDetailsId,
            final @RequestBody UserDetails userDetails) throws InvalidUserDetailsException {
        final UserDetails updatedUserDetails
                = userDetailsService.updateUserDetails(userDetailsId, userDetails);

        return new ResponseEntity<>(updatedUserDetails, HttpStatus.OK);
    }
}
