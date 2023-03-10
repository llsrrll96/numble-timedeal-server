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
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductEntity product;
    private int limitedAmount;
    private int sale_price;
    private LocalDateTime startDatetime;

    @Builder
    public Timedeal(ProductEntity product, int limitedAmount, int sale_price, LocalDateTime startDatetime) {
        this.product = product;
        this.limitedAmount = limitedAmount;
        this.sale_price = sale_price;
        this.startDatetime = startDatetime;
    }

    public void setLimitedAmount(int limitedAmount) {
        this.limitedAmount = limitedAmount;
    }
}
