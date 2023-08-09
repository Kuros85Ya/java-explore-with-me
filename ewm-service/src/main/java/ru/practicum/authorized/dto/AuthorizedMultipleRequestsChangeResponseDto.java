package ru.practicum.authorized.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthorizedMultipleRequestsChangeResponseDto {
    private List<AuthorizedRequestResponseDto> confirmedRequests;
    private List<AuthorizedRequestResponseDto> rejectedRequests;
}
