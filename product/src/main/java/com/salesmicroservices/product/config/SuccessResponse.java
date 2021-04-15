package com.salesmicroservices.product.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {

    private String message;
    private Integer statusCode;
    private HttpStatus httpStatus;

    public static SuccessResponse sendSuccess(String message) {
        var ok = HttpStatus.OK;
        return SuccessResponse
            .builder()
            .statusCode(ok.value())
            .httpStatus(ok)
            .message(message)
            .build();
    }
}
