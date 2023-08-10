package ru.practicum.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatsGetRequestDto {
    @NotEmpty
    private String startDttm;
    @NotEmpty
    private String endDttm;
    @NotNull
    private Boolean unique;
    private List<String> uris;
}
