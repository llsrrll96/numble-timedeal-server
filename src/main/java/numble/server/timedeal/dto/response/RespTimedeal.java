package numble.server.timedeal.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import numble.server.timedeal.domain.product.ProductDto;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespTimedeal {
    private Long timedealId;
    private ProductDto product;
    private int limitedAmount;
    private int sale_price;
    private LocalDateTime startDatetime;
}
