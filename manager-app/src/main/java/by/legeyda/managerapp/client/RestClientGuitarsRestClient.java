package by.legeyda.managerapp.client;

import by.legeyda.managerapp.controllers.payload.NewGuitarPayload;
import by.legeyda.managerapp.entity.Guitar;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RequiredArgsConstructor
public class RestClientGuitarsRestClient implements GuitarsRestClient {

    private static final ParameterizedTypeReference<List<Guitar>> GUITARS_TYPE_REFERENCE =
            new ParameterizedTypeReference<List<Guitar>>() {
            };

    private final RestClient restClient;



    @Override
    public List<Guitar> findAll() {
        return restClient.get()
                .uri("/catalogue-api/products/guitars")
                .retrieve()
                .body(GUITARS_TYPE_REFERENCE);
    }

    @Override
    public Guitar createGuitar(String manufacturer, String model, int price, String description) {

        try {
            return restClient.post()
                    .uri("catalogue-api/products/guitars")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewGuitarPayload(manufacturer, model, price, description))
                    .retrieve()
                    .body(Guitar.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));

        }
    }

    @Override
    public Optional<Guitar> findGuitarById(int guitarId) {

        try {
            return Optional.ofNullable(restClient.get()
                    .uri("/catalogue-api/products/guitars/{guitarId}", guitarId)
                    .retrieve()
                    .body(Guitar.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }

    @Override
    public void updateGuitar(int guitarId, String manufacturer, String model, int price, String description) {


        try {
            restClient.patch()
                    .uri("catalogue-api/products/guitars/{guitarId}", guitarId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewGuitarPayload(manufacturer, model, price, description))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));

        }
    }

    @Override
    public void deleteGuitar(int guitarId) {
        try {
            restClient.delete()
                    .uri("catalogue-api/products/guitars/{guitarId}", guitarId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new NoSuchElementException(exception);

        }
    }
}
