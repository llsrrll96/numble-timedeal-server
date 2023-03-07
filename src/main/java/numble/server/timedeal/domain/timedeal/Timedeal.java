package numble.server.timedeal.domain.timedeal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.server.timedeal.domain.product.ProductEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Timedeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timedealId;
    @JoinColumn(name = "productId")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;
    private int limitedAmount;
    private LocalDateTime startDatetime;

    @Builder
    public Timedeal(ProductEntity product, int limitedAmount, LocalDateTime startDatetime) {
        this.product = product;
        this.limitedAmount = limitedAmount;
        this.startDatetime = startDatetime;
    }
}
