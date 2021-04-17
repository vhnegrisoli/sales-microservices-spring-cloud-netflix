package com.salesmicroservices.sales.config;

import com.salesmicroservices.product.modules.jwt.provider.JwtTokenProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeignClientAuthInterceptor implements RequestInterceptor {

    private static final String BEARER = "Bearer ";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void apply(RequestTemplate template) {
        template
            .header("Authorization", BEARER.concat(jwtTokenProvider.getAccessToken()));
    }
}
