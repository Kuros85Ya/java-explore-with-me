package ru.practicum.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class StatDb {
    @Id
    private Long id;
    private String app;
    private String uri;
    private Long count;

    public StatDb(String app, String uri, Long count) {
        this.app = app;
        this.uri = uri;
        this.count = count;
    }
}