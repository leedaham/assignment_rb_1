package me.hamtom.redblue.assignment.callapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hamtom.redblue.assignment.callapi.dto.RequestSMS;
import me.hamtom.redblue.assignment.callapi.dto.ResponseSMS;
import me.hamtom.redblue.assignment.common.exception.PredictableRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private final CallApiService callApiService;

    //Request 정보 정의
    @Value("${sms.api.url}")
    private String url;
    @Value("${sms.api.uri}")
    private String uri;
    private static final HttpMethod SEND_SMS_HTTP_METHOD = HttpMethod.POST;
    private static final HttpHeaders headers = new HttpHeaders();
    private static final RequestSMS requestSMS = new RequestSMS();

    static {
        //Header 설정
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer Token");

        //RequestSMS 설정
        requestSMS.setTitle("SMS Title 샘플");
        requestSMS.setContent("안녕하세요! SMS 샘플 테스트입니다.");
        requestSMS.setTargetPhoneNumber("+82-10-1234-1234");
    }

    /**
     * RestTemplate API 호출
     */
    public ResponseSMS sendSmsWithRestTemplateCall() throws JsonProcessingException {
        log.info("sendSMS[RestTemplate] req, fullUrl: {}, method: {}, body: {}]", url+uri, SEND_SMS_HTTP_METHOD, requestSMS);


        //Request 객체 -> JSON
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(requestSMS);

        //호출 및 응답
        ResponseEntity<String> response = callApiService.callWithRestTemplate(url + uri, SEND_SMS_HTTP_METHOD, headers, jsonStr);


        //응답 확인
        HttpStatusCode responseStatusCode = response.getStatusCode();
        if (responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(200))) {
            String body = response.getBody();
            log.info("sendSMS[RestTemplate] resp, code: {}, body: {}]", responseStatusCode, body);
            return mapper.readValue(body, ResponseSMS.class);
        } else if (
                responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(403))
                || responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(404))
                || responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(500))
        ) {

            log.warn("sendSMS[RestTemplate] resp, error: {}]", responseStatusCode);
            throw new PredictableRuntimeException("오류가 발생했습니다. HttpStatus: " + responseStatusCode);

        } else {

            log.warn("sendSMS[RestTemplate] resp, error: {}]", responseStatusCode);
            throw new RuntimeException();

        }
    }

    /**
     * WebClient API 호출 (비동기)
     */
    public Mono<ResponseSMS> sendSmsWithWebClientCall(){
        log.info("sendSMS[WebClient] req, fullUrl: {}, method: {}, body: {}]", url+uri, SEND_SMS_HTTP_METHOD, requestSMS);
        Mono<ResponseEntity<String>> responseEntityMono = callApiService.callWithWebClient(url, uri, SEND_SMS_HTTP_METHOD, headers, requestSMS);
        ObjectMapper mapper = new ObjectMapper();
        Mono<ResponseSMS> result =
                responseEntityMono
                        .map(re -> {
                            String body = re.getBody();
                            try {
                                return mapper.readValue(body, ResponseSMS.class);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        });
        result.subscribe(s -> {
            log.info("sendSMS[WebClient] resp, code: {}, body: {}]", HttpStatusCode.valueOf(200), s);
        });
        return result;
    }

}
