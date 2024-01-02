package me.hamtom.redblue.assignment.bulk.dto;

import lombok.Data;

@Data
public class BulkInsertResultDto {
    private int totalRequestCount;
    private int successRequestCount;
    private int failRequestCount;

    public BulkInsertResultDto(int totalRequestCount, int successRequestCount, int failRequestCount) {
        this.totalRequestCount = totalRequestCount;
        this.successRequestCount = successRequestCount;
        this.failRequestCount = failRequestCount;
    }
}
