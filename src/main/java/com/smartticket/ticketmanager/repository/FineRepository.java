package com.smartticket.ticketmanager.repository;

import com.smartticket.ticketmanager.repository.entities.Fine;
import com.smartticket.ticketmanager.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findByUser(User user);
}
