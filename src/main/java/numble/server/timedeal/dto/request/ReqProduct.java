package numble.server.timedeal.dto.request;

import lombok.Data;

@Data
public class ReqProduct {
    private String user_id;
    private String category_code;
    private String product_code;
    private String product_name;
    private String product_desc;
    private int product_price;
    private int sale_price;
    private int product_amount;
}
