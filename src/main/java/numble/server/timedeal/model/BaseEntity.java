package numble.server.timedeal.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //Entity에서 Table에 대한 공통 매핑 정보가 필요할 때 부모 클래스에 정의하고 상속받아 해당 필드를 사용하여 중복을 제거
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseEntity { // auditing을 위한 entity
    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedDate() {
        return createdAt;
    }
}
