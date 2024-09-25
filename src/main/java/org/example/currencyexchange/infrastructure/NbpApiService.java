package org.example.currencyexchange.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NbpApiService implements ApiService {
    private final RestTemplate restTemplate;

    @Override
    public String getApiJson() {
        ResponseEntity<String> exchange = restTemplate.exchange("https://api.nbp.pl/api/exchangerates/tables/c/?format=json",
                HttpMethod.GET, null, String.class);
        return exchange.getBody();
    }
}