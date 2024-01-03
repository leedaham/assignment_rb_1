package me.hamtom.redblue.assignment.bulk.dto;

import lombok.Data;

@Data
public class BulkInsertResultDto {
    private int totalRequestCount;
    private int failRequestCount;

    public BulkInsertResultDto(int totalRequestCount, int failRequestCount) {
        this.totalRequestCount = totalRequestCount;
        this.failRequestCount = failRequestCount;
    }
}
