package numble.server.timedeal.domain.purchase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReqPurchase {
    @JsonProperty(value = "user_id")
    private String userId;
    @JsonProperty(value = "timedeal_id")
    private Long timedealId;
    @JsonProperty(value = "product_id")
    private Long productId;
    private int count;
    private int price;
}
