package com.bits_wilp.bookexchangeplatform.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@ToString // Generates a toString() method
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JWTRequest {
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
