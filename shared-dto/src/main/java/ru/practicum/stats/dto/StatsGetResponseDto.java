package ru.practicum.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatsGetResponseDto {
    @NotEmpty
    private String app;
    @NotEmpty
    private String uri;
    @NotNull
    private Long hits;
}
