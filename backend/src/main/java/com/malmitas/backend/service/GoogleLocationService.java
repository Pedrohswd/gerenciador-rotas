package com.malmitas.backend.service;

import com.malmitas.backend.model.dtos.response.DistanceMatrixResponse;
import com.malmitas.backend.model.dtos.response.GeoLocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GoogleLocationService {

    private final WebClient webClient;

    public GoogleLocationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://maps.googleapis.com").build();
    }

    public Mono<DistanceMatrixResponse> getGoogleLocation(double latAtual, double lonAtual, double latDest, double lonDest, String key) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/maps/api/distancematrix/json")
                        .queryParam("key", key)
                        .queryParam("origins", latAtual+","+lonAtual)
                        .queryParam("destinations", latDest+","+lonDest)
                        .build())
                .retrieve()
                .bodyToMono(DistanceMatrixResponse.class);
    }
}
