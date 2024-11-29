package com.malmitas.backend.service;

import com.malmitas.backend.model.dtos.response.GeoLocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeoLocationService {

    private final WebClient webClient;

    public GeoLocationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://nominatim.openstreetmap.org").build();
    }

    public Mono<GeoLocationResponse[]> getGeoLocation(String street, String county, String city, String state) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.php")
                        .queryParam("street", street)
                        .queryParam("county", county)
                        .queryParam("city", city)
                        .queryParam("state", state)
                        .queryParam("country", "Brazil")
                        .queryParam("polygon_geojson", 1)
                        .queryParam("format", "jsonv2")
                        .build())
                .retrieve()
                .bodyToMono(GeoLocationResponse[].class);
    }
}
