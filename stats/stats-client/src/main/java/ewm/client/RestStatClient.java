package ewm.client;

import ewm.ParamDto;
import ewm.ParamHitDto;
import ewm.ViewStats;
import ewm.exception.InvalidRequestException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class RestStatClient implements StatClient {
    private final String statUrl = "http://localhost:9090";
    private final RestClient restClient;

    public RestStatClient() {
        this.restClient = RestClient.builder()
                .baseUrl(statUrl)
                .build();
    }

    @Override
    public void hit(ParamHitDto paramHitDto) {
        restClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(paramHitDto)
                .retrieve()
                .onStatus(status -> status != HttpStatus.CREATED, (request, response) -> {
                    throw new InvalidRequestException(response.getStatusCode().value() + ": " + response.getBody());
                });
    }

    @Override
    public List<ViewStats> getStat(ParamDto paramDto) {
        return restClient.get().uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", paramDto.getStart().toString())
                        .queryParam("end", paramDto.getEnd().toString())
                        .queryParam("uris", paramDto.getUris())
                        .queryParam("unique", paramDto.getUnique())
                        .build())
                .retrieve()
                .onStatus(status -> status != HttpStatus.OK, (request, response) -> {
                    throw new InvalidRequestException(response.getStatusCode().value() + ": " + response.getBody());
                })
                .body(ParameterizedTypeReference.forType(List.class));
    }
}