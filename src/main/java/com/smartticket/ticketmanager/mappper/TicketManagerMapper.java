package com.smartticket.ticketmanager.mappper;

import com.smartticket.ticketmanager.dto.FineResponseDTO;
import com.smartticket.ticketmanager.repository.entities.Fine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketManagerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "deadlinePaymentDate", source = "deadlinePaymentDate")
    @Mapping(target = "paid", source = "paid")
    @Mapping(target = "fiscalId", source = "fiscal.id")
    @Mapping(target = "passengerId", source = "passenger.id")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    FineResponseDTO toFineResponseDTO(Fine createdFine);

    List<FineResponseDTO> toFineResponseDTOList(List<Fine> allFines);
}
