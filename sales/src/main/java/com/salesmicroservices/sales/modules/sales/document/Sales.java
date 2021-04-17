package com.salesmicroservices.sales.modules.sales.document;

import com.salesmicroservices.sales.modules.product.dto.ProductResponse;
import com.salesmicroservices.sales.modules.sales.SalesStatus;
import com.salesmicroservices.sales.modules.sales.dto.SalesProductRequest;
import com.salesmicroservices.sales.modules.sales.dto.SalesRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.util.ObjectUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sales")
public class Sales {

    private static final Integer TWO_DECIMAL = 2;

    @Id
    private String id;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime lastUpdate = LocalDateTime.now();

    private SalesStatus status = SalesStatus.PENDING;

    private List<SalesProduct> products;

    public BigDecimal calculateTotalSales() {
        return BigDecimal
            .valueOf(
                products
                    .stream()
                    .filter(product -> !isEmpty(product.getQuantity())
                        && !isEmpty(product.getProduct().getPrice()))
                    .map(product -> product.getQuantity() * product.getProduct().getPrice())
                    .reduce(BigDecimal.ZERO.doubleValue(), Double::sum)
            )
            .setScale(TWO_DECIMAL, RoundingMode.HALF_UP);
    }

    public static Sales convertFrom(SalesRequest request, List<ProductResponse> products) {
        var sales = new Sales();
        sales.setProducts(sales.createProductsList(request.getProducts(), products));
        return sales;
    }

    private List<SalesProduct> createProductsList(List<SalesProductRequest> request,
                                                  List<ProductResponse> products) {
        return IntStream
            .range(BigDecimal.ZERO.intValue(), products.size())
            .mapToObj(i -> new SalesProduct(products.get(i), getQuantity(request,products.get(i).getProductId())))
            .collect(Collectors.toList());
    }

    private Integer getQuantity(List<SalesProductRequest> request,
                                Integer productId) {
        return request
            .stream()
            .filter(productRequest -> productRequest.getProductId().equals(productId))
            .map(SalesProductRequest::getQuantity)
            .reduce(BigDecimal.ZERO.intValue(), Integer::sum);
    }
}
