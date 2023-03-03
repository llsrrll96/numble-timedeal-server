package numble.server.timedeal.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import numble.server.timedeal.model.UserEnum;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RespUser {
    private String userId;
    private String name;
    private String nickname;
    private String phone;
    private String email;
    private String profile;
    private String emailCheck;
    private UserEnum role;
    private LocalDateTime createdAt;
}
