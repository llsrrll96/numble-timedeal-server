package numble.server.timedeal.domain.timedeal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReqTimedeal{
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("limited_amount")
    private int limitedAmount;
    @JsonProperty("sale_price")
    private int salePrice;
    @JsonProperty("start_datetime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmm")
    private LocalDateTime startDatetime;
}
