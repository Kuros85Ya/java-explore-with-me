package ru.practicum.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatsHitRequestDto {
    @NotEmpty
    private String app;
    @NotEmpty
    private String uri;
    @NotNull
    private String ip;
    @NotNull
    private String timestamp;
}
