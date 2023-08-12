package ru.practicum.common.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "events", schema = "public")
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String annotation;
    private String description;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @ManyToOne
    private Location location;
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    private String title;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User creator;
    @Column(name = "creation_dttm")
    private LocalDateTime createdOn;
    @Column(name = "publication_dttm")
    private LocalDateTime publishedOn;
    private String status;
    @ManyToMany(mappedBy = "events")
    private List<Compilation> compilations;
    @OneToMany(mappedBy = "event")
    private Set<Request> requests;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(getId(), event.getId()) && Objects.equals(getAnnotation(), event.getAnnotation()) && Objects.equals(getDescription(), event.getDescription()) && Objects.equals(getEventDate(), event.getEventDate()) && Objects.equals(getLocation(), event.getLocation()) && Objects.equals(getPaid(), event.getPaid()) && Objects.equals(getParticipantLimit(), event.getParticipantLimit()) && Objects.equals(getRequestModeration(), event.getRequestModeration()) && Objects.equals(getTitle(), event.getTitle()) && Objects.equals(getCategory(), event.getCategory()) && Objects.equals(getCreator(), event.getCreator()) && Objects.equals(getCreatedOn(), event.getCreatedOn()) && Objects.equals(getPublishedOn(), event.getPublishedOn()) && Objects.equals(getStatus(), event.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAnnotation(), getDescription(), getEventDate(), getLocation(), getPaid(), getParticipantLimit(), getRequestModeration(), getTitle(), getCategory(), getCreator(), getCreatedOn(), getPublishedOn(), getStatus());
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", annotation='" + annotation + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", location=" + location +
                ", paid=" + paid +
                ", participantLimit=" + participantLimit +
                ", requestModeration=" + requestModeration +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", creator=" + creator +
                ", createdOn=" + createdOn +
                ", publishedOn=" + publishedOn +
                ", status='" + status + '\'' +
                '}';
    }
}
