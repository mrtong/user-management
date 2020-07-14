package com.foo.ing.usermanagement;

import com.foo.ing.usermanagement.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails,Integer> {
}
