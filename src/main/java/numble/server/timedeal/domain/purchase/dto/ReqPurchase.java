package numble.server.timedeal.domain.purchase.dto;

import lombok.Data;

@Data
public class ReqPurchase {
    private String user_id;
    private Long timedeal_id;
    private Long product_id;
    private int count;
    private int price;
}
