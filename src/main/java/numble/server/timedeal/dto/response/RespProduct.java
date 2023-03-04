package numble.server.timedeal.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespProduct {
    private Long productId;
    private String categoryCode;
    private String userId;
    private String timedealId;
    private String productCode;
    private String productName;
    private String productDesc;
    private int productAmount;
    private int productPrice;
    private int salePrice;
}
