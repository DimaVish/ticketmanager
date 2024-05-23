package com.smartticket.ticketmanager.exception;

import lombok.*;
import org.springframework.http.HttpStatus;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    HttpStatus httpStatus;
    String message;

}
