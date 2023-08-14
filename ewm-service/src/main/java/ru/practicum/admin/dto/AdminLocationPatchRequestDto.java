package ru.practicum.admin.dto;

import lombok.Data;

@Data
public class AdminLocationPatchRequestDto {
   private Float latitude;
   private Float longitude;
   private String description;
}
