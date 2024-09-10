package by.legeyda.managerapp.config;


import by.legeyda.managerapp.client.RestClientGuitarsRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientGuitarsRestClient guitarsRestClient(
            @Value("${guitarshop.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri) {
        return new RestClientGuitarsRestClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                //.requestInterceptor(new BasicAuthenticationInterceptor())
                .build());
    }

}
