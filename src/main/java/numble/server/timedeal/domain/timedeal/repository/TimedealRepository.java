package numble.server.timedeal.domain.timedeal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface TimedealRepository extends JpaRepository<Timedeal,Long> {
    int deleteByTimedealId(Long timedealid);

    Timedeal findByTimedealId(Long timedealid);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select t from Timedeal t where t.timedealId = :timedealId")
    Timedeal findByIdWithPessimisticLock(@Param("timedealId") Long timedealId);


    @Lock(value = LockModeType.OPTIMISTIC)
    @Query("select t from Timedeal t where t.timedealId = :timedealId")
    Timedeal findByIdWithOptimisticLock(@Param("timedealId")Long timedealId);
}
