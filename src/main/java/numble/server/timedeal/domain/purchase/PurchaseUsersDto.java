package numble.server.timedeal.domain.purchase;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseUsersDto {
    private Long productId;
    private List<String> users;
}
