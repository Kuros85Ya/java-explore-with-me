package ru.practicum.admin.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AdminUserRequestDto {
   @NotBlank
   @Size(min = 6)
   @Size(max = 254)
   @Email
   String email;
   @Size(min = 2)
   @Size(max = 250)
   @NotBlank
   String name;
}
