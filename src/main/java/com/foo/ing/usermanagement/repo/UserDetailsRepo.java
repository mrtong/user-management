package com.foo.ing.usermanagement.repo;

import com.foo.ing.usermanagement.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer> {
}
