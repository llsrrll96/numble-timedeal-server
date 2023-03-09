package numble.server.timedeal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqSignup {
    private String nickname;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
}
