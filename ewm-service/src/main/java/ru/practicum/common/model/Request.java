package ru.practicum.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.common.enums.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "requests", schema = "public")
public class Request {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    private User requester;
    @ManyToOne
    private Event event;
    private RequestStatus status;
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request request = (Request) o;
        return Objects.equals(getId(), request.getId()) && Objects.equals(getRequester(), request.getRequester()) && Objects.equals(getEvent(), request.getEvent()) && getStatus() == request.getStatus() && Objects.equals(getCreated(), request.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRequester(), getEvent(), getStatus(), getCreated());
    }
}
