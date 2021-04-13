package com.salesmicroservices.product.modules.product;

import com.salesmicroservices.product.modules.jwt.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ProductController {

    @Autowired
    private AuthUserService authUserService;

    @GetMapping("find")
    public ResponseEntity<HashMap<String, Object>> getProducts() {
        var data = new HashMap<String, Object>();
        
        data.put("data", "Ok");
        data.put("status", HttpStatus.OK);
        data.put("currentAuthUser", authUserService.getAuthUser());

        return ResponseEntity.ok(data);
    }
}
