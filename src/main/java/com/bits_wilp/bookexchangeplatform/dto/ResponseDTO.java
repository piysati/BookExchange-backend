package com.bits_wilp.bookexchangeplatform.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString // Generates a toString() method
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String msg;
    private HttpStatus statusCode;
}
