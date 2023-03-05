package numble.server.timedeal.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long productId;
    private String categoryCode;
    private String userId;
    private Long timedealId;
    private String productCode;
    private String productName;
    private String productDesc;
    private int productAmount;
    private int productPrice;
    private int salePrice;
}
