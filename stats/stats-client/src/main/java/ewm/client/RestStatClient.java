package ewm.client;

import ewm.ParamDto;
import ewm.ParamHitDto;
import ewm.ViewStats;
import ewm.exception.InvalidRequestException;
import ewm.exception.StatsServerUnavailable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
public class RestStatClient implements StatClient {
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final String statUrl;

    public RestStatClient(DiscoveryClient discoveryClient, @Value("${stat.client.url}") String statUrl) {
        this.discoveryClient = discoveryClient;
        this.statUrl = statUrl;
        this.restClient = RestClient.create(getInstance().getUri().toString());
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

    private ServiceInstance getInstance() {
        try {
            ServiceInstance serviceInstance = discoveryClient
                    .getInstances(statUrl)
                    .getFirst();
            log.info("Получаем {} uri: {}", statUrl, serviceInstance.getUri().toString());
            return serviceInstance;
        } catch (Exception exception) {
            throw new StatsServerUnavailable(
                    "Ошибка обнаружения адреса сервиса статистики с id: " + statUrl
            );
        }
    }
}