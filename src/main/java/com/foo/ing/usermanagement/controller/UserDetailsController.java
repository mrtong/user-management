package com.foo.ing.usermanagement.controller;

import com.foo.ing.usermanagement.model.UserDetails;
import com.foo.ing.usermanagement.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping(path = "/api/userdetails")
public class UserDetailsController {
    private UserDetailsService userDetailsService;

    public UserDetailsController(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping(
            value = "{id}",
            produces = {"application/json"})
    public ResponseEntity<UserDetails> getUserDetailsById(
            final @PathVariable(name="id") Integer userDetailsId) {
        final UserDetails userDetails = userDetailsService.findUserDetailsById(userDetailsId);
        if (userDetails == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "There is no user details found for userDetails id " + userDetailsId);
        }
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }
}
