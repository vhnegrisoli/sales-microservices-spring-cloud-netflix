package com.salesmicroservices.product.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogResponse {

    private String data;
    private Integer status;

    public static LogResponse create(Object body, Integer status) {
        var response = new LogResponse();
        response.setStatus(status);
        if (!isEmpty(body)) {
            try {
                response.setData(new ObjectMapper().writeValueAsString(body));
            } catch (Exception ex) {
                response.setData(null);
            }
        }
        return response;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception ex) {
            return "{}";
        }
    }
}
