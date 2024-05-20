package com.smartticket.ticketmanager.repository;

import com.smartticket.ticketmanager.repository.entities.Role;
import com.smartticket.ticketmanager.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByRole(Role role);

}
