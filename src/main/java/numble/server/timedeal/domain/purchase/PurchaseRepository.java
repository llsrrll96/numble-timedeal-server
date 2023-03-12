package numble.server.timedeal.domain.purchase;

import numble.server.timedeal.domain.product.ProductEntity;
import numble.server.timedeal.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    @Query(value = "select purchase.user.userId "+
    "from Purchase purchase "+
    "where purchase.product = :product")
    List<String> findUsersForTimedealPurchase(@Param ("product") ProductEntity product);

    @Query(value = "select purchase.product.productId "+
    "from Purchase purchase "+
    "where purchase.user = :user")
    List<Long> findProductsForUserPurchase(@Param("user") UserEntity user);
}
