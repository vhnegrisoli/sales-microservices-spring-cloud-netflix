package com.salesmicroservices.sales.modules.sales;

import com.salesmicroservices.sales.modules.jwt.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
public class SalesController {

    @Autowired
    private AuthUserService authUserService;

    @GetMapping("find")
    public Mono<HashMap<String, Object>> getSales() {
        var data = new HashMap<String, Object>();

        data.put("data", "Ok");
        data.put("status", HttpStatus.OK.value());
        data.put("currentAuthUser", authUserService.getAuthUser());

        return Mono.just(data);
    }
}
