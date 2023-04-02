package numble.server.timedeal.domain.purchase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseUsersDto {
    private Long productId;
    private List<String> users;
}
