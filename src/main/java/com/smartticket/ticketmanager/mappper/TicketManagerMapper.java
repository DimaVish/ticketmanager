package com.smartticket.ticketmanager.mappper;

import com.smartticket.ticketmanager.dto.FineResponseDTO;
import com.smartticket.ticketmanager.dto.TicketDTO;
import com.smartticket.ticketmanager.dto.UserDTO;
import com.smartticket.ticketmanager.repository.entities.Fine;
import com.smartticket.ticketmanager.repository.entities.Ticket;
import com.smartticket.ticketmanager.repository.entities.User;
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

    @Mapping(target = "email", source = "email")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phone", source = "phone")
    UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOList(List<User> allUsersByRole);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "tripId", source = "trip.id")
    @Mapping(target = "expireTime", source = "expireTime")
    @Mapping(target = "qrCode", source = "qrCode")
    @Mapping(target = "purchaseDate", source = "purchaseTime")
    TicketDTO toTicketDTO(Ticket savedTicket);

    List<TicketDTO> toTicketDTOList(List<Ticket> ticketList);
}
