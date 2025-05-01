package com.example.demo.utils.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ApplicationResponse<Data> {
    @JsonProperty("code")
    private int code;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("data")
    private Data data;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    public ApplicationResponse(int code, boolean success, Data data) {
        this.code = code;
        this.success = success;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
