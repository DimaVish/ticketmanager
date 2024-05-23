package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.TripDTO;
import com.smartticket.ticketmanager.exception.BusinessException;
import com.smartticket.ticketmanager.repository.TripRepository;
import com.smartticket.ticketmanager.repository.entities.Trip;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;


    public Trip createTrip(TripDTO tripDTO) {

        if (tripRepository.findByRoute(tripDTO.getRoute()).isPresent()) {
            throw new BusinessException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Trip already exists");
        }

        Trip trip = new Trip();
        trip.setRoute(tripDTO.getRoute());
        trip.setDateTime(tripDTO.getDateTime());
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
        return tripRepository.findById(travelId).orElseThrow(() -> new EntityNotFoundException("Trip Not Found"));
    }

    public List<Trip> findTripsByDate(Date date, int page, int size) {
        Instant instant = date.toInstant();
        Pageable pageable = PageRequest.of(page, size);
        return tripRepository.findAllByDateTime(instant, pageable).getContent();
    }

    public List<Trip> findAllTrips(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return tripRepository.findAll(pageable).getContent();
    }

    public void deleteTrip(Long tripId) {
        tripRepository.deleteById(tripId);
    }

}
