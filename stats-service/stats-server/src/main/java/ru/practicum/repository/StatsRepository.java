package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Hit;
import ru.practicum.model.StatDb;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Integer> {

    @Query("select new ru.practicum.model.StatDb(h.app, h.uri, COUNT(h.ip))" +
            "from Hit as h " +
            "where h.created > :startDt and h.created < :endDt " +
            "and h.uri IN (:uris) " +
            "group by h.app, h.uri " +
            "order by 3 desc")
    List<StatDb> getHitsByAppAndUris(LocalDateTime startDt, LocalDateTime endDt, @Param("uris") List<String> uris);

    @Query("select new ru.practicum.model.StatDb(h.app, h.uri, COUNT(distinct h.ip))" +
            "from Hit as h " +
            "where h.created > :startDt and h.created < :endDt " +
            "and h.uri IN (:uris) " +
            "group by h.app, h.uri " +
            "order by 3 desc")
    List<StatDb> getHitsByAppAndUrisUnique(LocalDateTime startDt, LocalDateTime endDt, @Param("uris") List<String> uris);

    @Query("select new ru.practicum.model.StatDb(h.app, h.uri, COUNT(h.ip))" +
            "from Hit as h " +
            "where h.created > :startDt and h.created < :endDt " +
            "group by h.app, h.uri " +
            "order by 3 desc")
    List<StatDb> getHitsUnique(LocalDateTime startDt, LocalDateTime endDt);

    @Query("select new ru.practicum.model.StatDb(h.app, h.uri, COUNT(distinct h.ip))" +
            "from Hit as h " +
            "where h.created > :startDt and h.created < :endDt " +
            "group by h.app, h.uri " +
            "order by 3 desc")
    List<StatDb> getHits(LocalDateTime startDt, LocalDateTime endDt);
}
