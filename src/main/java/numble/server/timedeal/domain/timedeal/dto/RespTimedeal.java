package numble.server.timedeal.domain.timedeal.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import numble.server.timedeal.domain.product.dto.ProductDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespTimedeal {
    private Long timedealId;
    private ProductDto product;
    private int limitedAmount;
    @JsonProperty("sale_price")
    private int salePrice;
    private LocalDateTime startDatetime;
}
