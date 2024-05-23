package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.TripDTO;
import com.smartticket.ticketmanager.exception.BusinessException;
import com.smartticket.ticketmanager.repository.TripRepository;
import com.smartticket.ticketmanager.repository.entities.Trip;
import com.smartticket.ticketmanager.repository.entities.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserService userService;

    public Trip createTrip(TripDTO tripDTO) {

        Optional<Trip> tripByRoute = tripRepository.findByRoute(tripDTO.getRoute());
        if (tripByRoute.isPresent()) {
            throw new BusinessException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Trip already exists");
        }
        User passenger = userService.findUserById(tripDTO.getPassengerId());

        Trip trip = new Trip();
        trip.setRoute(tripDTO.getRoute());
        trip.setDateTime(tripDTO.getDateTime());
        trip.setPassenger(passenger);

        return tripRepository.save(trip);
    }


    public Trip updateTrip(Long tripId, TripDTO tripDTO) {

        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip Not Found"));

        if (!trip.getRoute().equals(tripDTO.getRoute())) {
            trip.setRoute(tripDTO.getRoute());
        }

        if (!trip.getDateTime().equals(tripDTO.getDateTime())) {
            trip.setDateTime(tripDTO.getDateTime());
        }
        return tripRepository.save(trip);
    }

    public Trip findTripById(Long travelId) {
        return tripRepository.findById(travelId).orElse(null);
    }

    public List<Trip> findTripsByDate(LocalDate date, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tripRepository.findByDate(date, pageable).getContent();
    }

    public List<Trip> findAllTrips(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tripRepository.findAll(pageable).getContent();
    }

    public void deleteTrip(Long tripId) {

        // Now delete the trip
        tripRepository.deleteById(tripId);
    }

    public void updateTripWithTicket(Trip trip) {
        tripRepository.save(trip);
    }
}
