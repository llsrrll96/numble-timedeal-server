package numble.server.timedeal.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByNickname(String nickname);
    UserEntity findByUserId(String userId);
}
