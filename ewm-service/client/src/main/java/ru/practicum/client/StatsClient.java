package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.dto.StatsGetRequestDto;
import ru.practicum.dto.StatsHitRequestDto;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> saveHit(StatsHitRequestDto requestDto) {
        return post("", requestDto);
    }

    public ResponseEntity<Object> getStats(StatsGetRequestDto requestDto) {
//        String path = UriComponentsBuilder.fromUriString("")
//                .queryParam("state", requestDto.name())
//                .queryParam("from", requestDto)
//                .queryParam("size", requestDto)
//                .buildAndExpand()
//                .toUriString();
//
        return get("path");
    }
}
