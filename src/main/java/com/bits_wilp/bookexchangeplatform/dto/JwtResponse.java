package com.bits_wilp.bookexchangeplatform.dto;

import lombok.*;

@Getter
@Setter
@ToString // Generates a toString() method
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private UserDTO userDto;

}
