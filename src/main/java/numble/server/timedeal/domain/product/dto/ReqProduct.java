package numble.server.timedeal.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReqProduct {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("category_code")
    private String categoryCode;

    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_desc")
    private String productDesc;

    @JsonProperty("product_price")
    private int productPrice;

    @JsonProperty("sale_price")
    private int salePrice;

    @JsonProperty("product_amount")
    private int productAmount;
}
