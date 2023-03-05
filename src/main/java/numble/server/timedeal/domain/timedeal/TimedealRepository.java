package numble.server.timedeal.domain.timedeal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimedealRepository extends JpaRepository<Timedeal,Long> {
    int deleteByTimedealId(Long timedealid);

    Timedeal findByTimedealId(Long timedealid);
}
