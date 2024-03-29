package numble.server.timedeal.domain.product.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.server.timedeal.domain.user.repository.UserEntity;
import numble.server.timedeal.util.BaseEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @JoinColumn(name = "categoryCode")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity userEntity;

    @Column(length = 30)
    private String productCode;

    @Column(length = 50)
    private String productName;

    @Lob
    private String productDesc;

    private int productAmount;

    private int productPrice;

    private int salePrice;

    @Builder
    public ProductEntity(Category category, UserEntity userEntity, String productCode, String productName, String productDesc, int productPrice, int salePrice, int productAmount) {
        this.category = category;
        this.userEntity = userEntity;
        this.productCode = productCode;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.salePrice = salePrice;
        this.productAmount = productAmount;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }
}
