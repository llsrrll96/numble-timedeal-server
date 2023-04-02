package numble.server.timedeal.domain.timedeal.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.server.timedeal.domain.product.repository.ProductEntity;

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
    private int salePrice;
    private LocalDateTime startDatetime;

    @Version
    private Integer version;

    @Builder
    public Timedeal(ProductEntity product, int limitedAmount, int salePrice, LocalDateTime startDatetime) {
        this.product = product;
        this.limitedAmount = limitedAmount;
        this.salePrice = salePrice;
        this.startDatetime = startDatetime;
    }

    public void setLimitedAmount(int limitedAmount) {
        this.limitedAmount = limitedAmount;
    }
}
