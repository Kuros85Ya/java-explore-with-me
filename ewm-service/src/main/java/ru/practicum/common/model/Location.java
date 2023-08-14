package ru.practicum.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "locations", schema = "public")
public class Location {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull
    private Float latitude;
    @NotNull
    private Float longitude;
    private String description;
    @OneToMany(mappedBy = "location")
    private List<Event> event;



    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(getId(), location.getId()) && Objects.equals(getLatitude(), location.getLatitude()) && Objects.equals(getLongitude(), location.getLongitude()) && Objects.equals(getDescription(), location.getDescription()) && Objects.equals(getEvent(), location.getEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLatitude(), getLongitude(), getDescription(), getEvent());
    }
}
