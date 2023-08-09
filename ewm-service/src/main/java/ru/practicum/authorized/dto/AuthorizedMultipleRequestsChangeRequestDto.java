package ru.practicum.authorized.dto;

import lombok.Data;
import ru.practicum.common.enums.RequestStatus;

import java.util.List;

@Data
public class AuthorizedMultipleRequestsChangeRequestDto {
    private List<Long> requestIds;
    private RequestStatus status;
}
