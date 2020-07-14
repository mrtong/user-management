package com.foo.ing.usermanagement.service;

import com.foo.ing.usermanagement.exception.UserDetailsNotFoundException;
import com.foo.ing.usermanagement.model.UserDetails;
import com.foo.ing.usermanagement.repo.UserDetailsRepo;
import com.foo.ing.usermanagement.util.MockDataGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {
    @Mock
    private UserDetailsRepo userDetailsRepo;

    private UserDetailsService userDetailsService;

    @Before
    public void setup() {
        userDetailsService = new UserDetailsService(userDetailsRepo);
    }

    @Test
    public void shouldReturnUserDetailsObjectWhenUserDetailsIsFound() {
        when(userDetailsRepo.findById(anyInt())).thenReturn(Optional.of(MockDataGenerator.generateOneUserDetails()));

        final UserDetails foundUserDetails =
                userDetailsService.findUserDetailsById(1);

        assertNotNull(foundUserDetails);
        assertEquals("test", foundUserDetails.getFirstName());

    }

    @Test
    public void updateUserDetails_shouldReturnUpdatedUserDetailsObjectWhenUserDetailsIsFound() {
        final UserDetails updatedUserDetails
                = MockDataGenerator.generateUpdatedUserDetails();

        when(userDetailsRepo.findById(anyInt())).thenReturn(Optional.of(MockDataGenerator.generateOneUserDetails()));
        when(userDetailsRepo.save(ArgumentMatchers.any())).thenReturn(updatedUserDetails);

        final UserDetails foundUserDetails =
                userDetailsService.updateUserDetails(1, updatedUserDetails);

        assertNotNull(foundUserDetails);
        assertEquals("updatedTest", foundUserDetails.getFirstName());

    }

    @Test(expected = UserDetailsNotFoundException.class)
    public void updateUserDetails_shouldRaiseExceptionWhenUserDetailsNotFound() {
        final UserDetails updatedUserDetails
                = MockDataGenerator.generateUpdatedUserDetails();

        when(userDetailsRepo.findById(anyInt())).thenReturn(Optional.empty());

        final UserDetails foundUserDetails =
                userDetailsService.updateUserDetails(1, updatedUserDetails);

    }

    @Test(expected = UserDetailsNotFoundException.class)
    public void shouldRaiseExceptionWhenUserDetailsNotFound() {
        when(userDetailsRepo.findById(anyInt())).thenReturn(Optional.empty());

        final UserDetails foundUserDetails =
                userDetailsService.findUserDetailsById(1);

    }

}