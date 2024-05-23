package com.smartticket.ticketmanager.repository;

import com.smartticket.ticketmanager.repository.entities.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {
    // Custom query to find all fines where the user is either a fiscal or a passenger
    @Query("SELECT f FROM Fine f WHERE f.passenger.id = :userId")
    List<Fine> findAllByUserId(@Param("userId") Long userId);

    List<Fine> findByPassengerId(Long passengerId);

    List<Fine> findByFiscalId(Long fiscalId);

}
