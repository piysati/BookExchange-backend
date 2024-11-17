package com.bits_wilp.bookexchangeplatform.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO{

    @NotBlank(message = "Name is mandatory")
    @NotNull(message = "Name can't be null")
    @Size(max = 35, message = "Name cannot contain more than 35 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Name cannot contain special characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @NotNull(message = "Email can't be null")
    @Email(message = "Email address must be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @NotNull(message = "Password can't be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one uppercase letter"),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase letter"),
            @Pattern(regexp = "(?=.*\\d).+", message = "Password must contain at least one digit"),
            @Pattern(regexp = "(?=.*[!@#$%^&*]).+", message = "Password must contain at least one special character")
    })
    private String password;

}
