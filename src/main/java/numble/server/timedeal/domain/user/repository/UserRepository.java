package numble.server.timedeal.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByNickname(String nickname);
    Optional<UserEntity> findByUserId(String userId);
}
