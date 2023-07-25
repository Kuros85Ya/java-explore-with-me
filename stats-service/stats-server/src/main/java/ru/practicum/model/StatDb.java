package ru.practicum.model;

import javax.persistence.Entity;
import javax.persistence.Id;

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

    public StatDb() {

    }

    public String getApp() {
        return app;
    }

    public String getUri() {
        return uri;
    }

    public Long getCount() {
        return count;
    }
}