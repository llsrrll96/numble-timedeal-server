package numble.server.timedeal.domain.user.dto;

import lombok.Data;

@Data
public class ReqSignin {
    private String nickname;
    private String password;
}
