package numble.server.timedeal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/v1/users")
    public ResponseEntity<String> findByUsers(){
        return new ResponseEntity<>("OK", HttpStatus.OK );
    }
}
