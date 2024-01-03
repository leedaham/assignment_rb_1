package me.hamtom.redblue.assignment.callapi;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class CallApiService {

    /**
     * RestTemplate을 이용한 API 요청 메소드
     * @param fullUrl 요청 URL+URI
     * @param method  요청 HTTP METHOD
     * @param headers 요청 HEADER
     * @param bodyJsonStr 요청 Body
     * @return 응답 값
     */
    public ResponseEntity<String> callWithRestTemplate(String fullUrl, HttpMethod method, HttpHeaders headers, String bodyJsonStr) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(
                    fullUrl,
                    method,
                    new HttpEntity<>(bodyJsonStr, headers),
                    String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    /**
     * WebClient를 이용한 API 요청 메소드
     * @param url 요청 URL
     * @param uri 요청 URI
     * @param method  요청 HTTP METHOD
     * @param headers 요청 HEADER
     * @param bodyObj 요청 Body
     * @return 응답 값
     */
    public Mono<ResponseEntity<String>> callWithWebClient(String url, String uri, HttpMethod method, HttpHeaders headers, Object bodyObj) {
        try {
            return WebClient
                    .create(url)
                    .method(method)
                    .uri(uri)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .bodyValue(bodyObj)
                    .retrieve()
                    .toEntity(String.class);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
