package me.hamtom.redblue.assignment.callapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.hamtom.redblue.assignment.callapi.dto.RequestSMS;
import me.hamtom.redblue.assignment.callapi.dto.ResponseSMS;
import me.hamtom.redblue.assignment.common.exception.PredictableRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final CallApiService callApiService;

    //Request 정보 정의
    @Value("sms.api.url")
    private String url;
    @Value("sms.api.uri")
    private String uri;
    private static final HttpMethod SEND_SMS_HTTP_METHOD = HttpMethod.POST;
    private RequestSMS defineRequestSMSBody() {
        RequestSMS requestSMS = new RequestSMS();
        requestSMS.setTitle("SMS Title 샘플");
        requestSMS.setContent("안녕하세요! SMS 샘플 테스트입니다.");
        requestSMS.setTargetPhoneNumber("+82-10-1234-1234");
        return requestSMS;
    }
    private HttpHeaders defineHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer Token");
        return headers;
    }


    public ResponseSMS sendSmsWithRestTemplateCall() throws JsonProcessingException {
        //Header
        HttpHeaders headers = defineHttpHeaders();

        //Request 객체
        RequestSMS requestSMS = defineRequestSMSBody();

        //Request 객체 -> JSON
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(requestSMS);

        //호출 및 응답
        ResponseEntity<String> response = callApiService.callWithRestTemplate(url+uri, SEND_SMS_HTTP_METHOD, headers, jsonStr);

        //응답 확인
        HttpStatusCode responseStatusCode = response.getStatusCode();
        if (responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(200))) {
            String body = response.getBody();
            return mapper.readValue(body, ResponseSMS.class);
        } else if (
                responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(403))
                        || responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(404))
                        || responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(500))
        ) {
            throw new PredictableRuntimeException("오류가 발생했습니다. HttpStatus: "+responseStatusCode);
        } else {
            throw new RuntimeException();
        }
    }

    public ResponseSMS sendSmsWithWebClientCall() throws JsonProcessingException {
        //Header
        HttpHeaders headers = defineHttpHeaders();

        //Request 객체
        RequestSMS requestSMS = defineRequestSMSBody();

        ResponseEntity<String> response = callApiService.callWithWebClient(url, uri, SEND_SMS_HTTP_METHOD, headers, requestSMS);
        //응답 확인
        HttpStatusCode responseStatusCode = response.getStatusCode();
        if (responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(200))) {
            String body = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(body, ResponseSMS.class);
        } else if (
                responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(403))
                        || responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(404))
                        || responseStatusCode.isSameCodeAs(HttpStatusCode.valueOf(500))
        ) {
            throw new PredictableRuntimeException("오류가 발생했습니다. HttpStatus: "+responseStatusCode);
        } else {
            throw new RuntimeException();
        }
    }

}
