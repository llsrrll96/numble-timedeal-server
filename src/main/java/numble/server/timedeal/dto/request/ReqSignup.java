package numble.server.timedeal.dto.request;

import lombok.Data;

@Data
public class ReqSignup {
    private String nickname;
    private String name;
    private String email;
    private String phone;
    private String password;
}
