package numble.server.timedeal.domain.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.server.timedeal.domain.product.ProductEntity;
import numble.server.timedeal.domain.user.UserEntity;
import numble.server.timedeal.model.BaseEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;
    @JoinColumn(name = "productId")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    private int count;
    private int price;

    @Builder
    public Purchase(ProductEntity product, UserEntity user, int count, int price) {
        this.product = product;
        this.user = user;
        this.count = count;
        this.price = price;
    }
}
