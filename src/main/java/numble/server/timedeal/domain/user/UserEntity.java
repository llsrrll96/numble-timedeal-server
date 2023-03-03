package numble.server.timedeal.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.server.timedeal.model.BaseEntity;
import numble.server.timedeal.model.IdGenerator;
import numble.server.timedeal.model.UserEnum;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @Column(length = 30)
    private String userId;
    @Column(length = 50)
    private String password;
    @Column(length = 20)
    private String name;
    @Column(length = 200)
    private String nickname;
    @Column(length = 20)
    private String phone;
    @Column(length = 50)
    private String email;
    @Column(length = 100)
    private String profile;
    @Column(length = 1)
    private String emailCheck;
    @Column(length = 10)
    private String grade;
    @Column(length = 10)
    private UserEnum role;

    @Builder
    public UserEntity(String password, String name, String nickname, String phone, String email) {
        this.userId = IdGenerator.generateId(email);
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.role = UserEnum.ROLE_USER;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setEmailCheck(String emailCheck) {
        this.emailCheck = emailCheck;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}