package ru.practicum.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "compilations", schema = "public")
public class Compilation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotEmpty
    private String title;
    private Boolean pinned;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "compilation_event",
            joinColumns = { @JoinColumn(name = "compilation_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    List<Event> events;
}
