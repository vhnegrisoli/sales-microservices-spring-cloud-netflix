package com.salesmicroservices.sales.modules.sales.document;

import com.salesmicroservices.sales.modules.jwt.dto.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesBuyer {

    private Integer userId;

    private String username;

    public static SalesBuyer convertFrom(AuthUser authUser) {
        return SalesBuyer
            .builder()
            .userId(authUser.getUserId())
            .username(authUser.getUsername())
            .build();
    }
}
