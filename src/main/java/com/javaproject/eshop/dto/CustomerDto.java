package com.javaproject.eshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    @Email(message = "Email not valid")
    @NotNull(message = "Email can not be null")
    @NotBlank(message = "You have to provide an email")
    private String email;

    @NotNull(message = "First name can not be null")
    @NotBlank(message = "First name can not be empty")
    private String firstName;

    @NotNull(message = "Last name can not be null")
    @NotBlank(message = "Last name can not be empty")
    private String lastName;

    @NotNull
    @NotBlank
    private String password;
}
