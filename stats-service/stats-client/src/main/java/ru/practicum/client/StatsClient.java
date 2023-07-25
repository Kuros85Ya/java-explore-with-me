package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.stats.dto.StatsGetRequestDto;
import ru.practicum.stats.dto.StatsHitRequestDto;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> saveHit(StatsHitRequestDto requestDto) {
        return post("/hit", requestDto);
    }

    public ResponseEntity<Object> getStats(StatsGetRequestDto requestDto) {
        String path = UriComponentsBuilder.fromUriString("/stats")
                .queryParam("start", requestDto.getStartDttm())
                .queryParam("end", requestDto.getEndDttm())
                .queryParam("unique", requestDto.getUnique())
                .queryParam("uris", requestDto.getUris())
                .buildAndExpand()
                .toUriString();

        return get(path);
    }
}
