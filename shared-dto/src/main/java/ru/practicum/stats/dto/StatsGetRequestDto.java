package ru.practicum.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatsGetRequestDto {
    @NotEmpty
    private LocalDateTime startDttm;
    @NotEmpty
    private LocalDateTime endDttm;
    @NotNull
    private Boolean unique;
    private List<String> uris;
}
