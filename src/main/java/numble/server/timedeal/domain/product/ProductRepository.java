package numble.server.timedeal.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    int deleteByProductId(Long productId);

    Optional<ProductEntity> findByProductId(Long productid);
}
