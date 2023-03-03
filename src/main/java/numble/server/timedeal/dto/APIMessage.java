package numble.server.timedeal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIMessage<T> {
    private String status;
    private String message;
    private T data;
}
