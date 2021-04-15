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
public class LogRequest {

    private String url;
    private String httpMethod;
    private List<String> parameters;
    private String body;

    public static LogRequest create(String url,
                                    HttpMethod httpMethod,
                                    Object body,
                                    String... parameters) {
        var request = new LogRequest();
        request.setUrl(url);
        request.setHttpMethod(httpMethod.name());
        request.setParameters(Arrays.stream(parameters).collect(Collectors.toList()));
        if (!isEmpty(body)) {
            try {
                request.setBody(new ObjectMapper().writeValueAsString(body));
            } catch (Exception ex) {
                request.setBody(null);
            }
        }
        return request;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception ex) {
            return "{}";
        }
    }
}
