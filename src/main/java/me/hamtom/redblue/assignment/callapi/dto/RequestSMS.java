package me.hamtom.redblue.assignment.callapi.dto;

import lombok.Data;

@Data
public class RequestSMS {
    private String title;
    private String content;
    private String targetPhoneNumber;

    public RequestSMS() {
    }
}
