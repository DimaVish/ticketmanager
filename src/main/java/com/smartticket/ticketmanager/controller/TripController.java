package com.smartticket.ticketmanager.controller;


import com.smartticket.ticketmanager.dto.TripDTO;
import com.smartticket.ticketmanager.repository.entities.Trip;
import com.smartticket.ticketmanager.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    @PreAuthorize("hasRole('ADMIN') or (hasRole('ROLE_PASSENGER') and #tripDTO.passengerId == principal.id)")
    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody @Valid TripDTO tripDTO) {
        return new ResponseEntity<>(tripService.createTrip(tripDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{tripId}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long tripId, @RequestBody TripDTO tripDTO) {
        return ResponseEntity.ok(tripService.updateTrip(tripId, tripDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Trip>> findAllTrips(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Trip> trips = tripService.findAllTrips(page, size);
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-date")
    public ResponseEntity<List<Trip>> findTripsByDate(@RequestParam LocalDate date, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Trip> trips = tripService.findTripsByDate(date, page, size);
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Trip> deleteTrip(@PathVariable Long tripId) {
        tripService.deleteTrip(tripId);
        return ResponseEntity.ok().build();
    }

}
