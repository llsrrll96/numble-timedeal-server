package numble.server.timedeal.domain.purchase.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.server.timedeal.domain.product.repository.ProductEntity;
import numble.server.timedeal.domain.user.repository.UserEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {
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
