package ru.practicum.authorized.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorizedMultipleRequestsChangeResponseDto {
    List<AuthorizedRequestResponseDto> confirmedRequests;
    List<AuthorizedRequestResponseDto> rejectedRequests;
}
