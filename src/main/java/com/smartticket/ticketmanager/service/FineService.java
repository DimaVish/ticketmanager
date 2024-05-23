package com.smartticket.ticketmanager.service;

import com.smartticket.ticketmanager.dto.FineDTO;
import com.smartticket.ticketmanager.dto.FineResponseDTO;
import com.smartticket.ticketmanager.exception.BusinessException;
import com.smartticket.ticketmanager.mappper.TicketManagerMapper;
import com.smartticket.ticketmanager.repository.FineRepository;
import com.smartticket.ticketmanager.repository.UserRepository;
import com.smartticket.ticketmanager.repository.entities.Fine;
import com.smartticket.ticketmanager.repository.entities.Role;
import com.smartticket.ticketmanager.repository.entities.User;
import com.smartticket.ticketmanager.security.model.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FineService {

    private final FineRepository fineRepository;
    private final UserRepository userRepository;
    private final TicketManagerMapper mapper;

    public List<FineResponseDTO> getAllFines() {

        List<Fine> allFines = fineRepository.findAll();
        return mapper.toFineResponseDTOList(allFines);

    }

    public List<FineResponseDTO> getFinesForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (Role.ROLE_PASSENGER.equals(user.getRole())) {
            return mapper.toFineResponseDTOList(fineRepository.findByPassengerId(userId));
        }

        List<Fine> allByUserId = fineRepository.findByFiscalId(userId);
        return mapper.toFineResponseDTOList(allByUserId);
    }

    public FineResponseDTO createFine(Long fiscalId, FineDTO fineDTO) {
        User fiscal = userRepository.findById(fiscalId)
                .orElseThrow(() -> new EntityNotFoundException("Fiscal with ID " + fiscalId + " not found"));
        User passenger = userRepository.findById(fineDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Passenger with ID " + fineDTO.getUserId() + " not found"));
        if (!Role.ROLE_PASSENGER.equals(passenger.getRole())) {
            throw new AccessDeniedException("Can not create Fine for User that not passenger");
        }
        Fine fine = new Fine();
        fine.setFiscal(fiscal);
        fine.setPassenger(passenger);
        fine.setValue(fineDTO.getValue());
        fine.setDeadlinePaymentDate(fineDTO.getDeadlinePaymentDate());
        fine.setPaid(false); // Initially, the fine is not paid
        Fine createdFine = fineRepository.save(fine);
        return mapper.toFineResponseDTO(createdFine);
    }

    public FineResponseDTO updateFine(Long fineId, FineDTO fineDTO) {
        Fine fine = fineRepository.findById(fineId).orElseThrow(() -> new EntityNotFoundException("Fine not found"));
        fine.setValue(fineDTO.getValue());
        fine.setDeadlinePaymentDate(fineDTO.getDeadlinePaymentDate());
        Fine updatedFine = fineRepository.save(fine);
        return mapper.toFineResponseDTO(updatedFine);
    }

    public void deleteFine(Long fineId) {
        fineRepository.deleteById(fineId);
    }

    public FineResponseDTO payFine(Long fineId, String paymentMethod) {
        if (paymentMethod == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Payment method could not be null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Fine fine = fineRepository.findById(fineId).orElseThrow(() -> new EntityNotFoundException("Fine not found"));
        if (!customUserDetails.getId().equals(fine.getPassenger().getId())) {
            throw new AccessDeniedException("Can not pay Fine that belongs to another passenger");
        }
        if (fine.isPaid()) {
            throw new BusinessException(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Fine already paid");
        }

        //Process payment
        log.info("Fine has been paid successfully");
        fine.setPaid(true);
        fine.setPaymentMethod(paymentMethod);
        Fine paidFine = fineRepository.save(fine);
        return mapper.toFineResponseDTO(paidFine);
    }
}