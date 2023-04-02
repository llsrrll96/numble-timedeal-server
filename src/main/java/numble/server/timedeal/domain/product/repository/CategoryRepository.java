package numble.server.timedeal.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
    int deleteByCategoryCode(String categoryCode);
}
