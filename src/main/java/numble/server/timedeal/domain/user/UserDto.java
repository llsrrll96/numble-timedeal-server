package numble.server.timedeal.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import numble.server.timedeal.model.UserEnum;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String userId;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String profile;
    private String emailCheck;
    private String grade;
    private UserEnum role;
}
