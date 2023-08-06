package ru.practicum.authorized.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthorizedMultipleRequestsChangeResponseDto {
    List<AuthorizedRequestResponseDto> confirmedRequests;
    List<AuthorizedRequestResponseDto> rejectedRequests;
}
