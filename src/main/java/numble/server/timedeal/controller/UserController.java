package numble.server.timedeal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.user.UserEntity;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.dto.request.ReqRole;
import numble.server.timedeal.dto.request.ReqSignin;
import numble.server.timedeal.dto.request.ReqSignup;
import numble.server.timedeal.dto.response.RespUser;
import numble.server.timedeal.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping ("/v1/users/signup")
    public ResponseEntity<APIMessage<Boolean>> signup(@RequestBody ReqSignup reqSignup){
        return new ResponseEntity<>(userService.signup(reqSignup), HttpStatus.CREATED );
    }

    @PostMapping ("/v1/users/signin")
    public ResponseEntity<APIMessage<Boolean>> signin(HttpSession session, @RequestBody ReqSignin reqSignin){
        if(userService.signin(reqSignin)){
            session.setAttribute("id", userService.findUserIdByNickname(reqSignin.getNickname()));
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"로그인",true), HttpStatus.OK );
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(),"로그인",false), HttpStatus.BAD_REQUEST );
    }

    @GetMapping("/v1/users/logout")
    public ResponseEntity<APIMessage<Boolean>> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"로그아웃",true), HttpStatus.OK );
    }
    @DeleteMapping("/v1/users/{userid}")
    public ResponseEntity<APIMessage<Boolean>> deleteUser(@PathVariable String userid){
        userService.deleteUser(userid);
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"회원탈퇴",true), HttpStatus.OK );
    }

    @GetMapping("/v1/users/{userid}")
    public ResponseEntity<APIMessage<RespUser>> userInfo(@PathVariable String userid){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"회원정보",userService.findById(userid)), HttpStatus.OK );
    }

    @GetMapping("/v1/users")
    public ResponseEntity<Page<UserEntity>> userPagination(@PageableDefault(size = 50)Pageable pageable){
        return new ResponseEntity<>(userService.userPagination(pageable), HttpStatus.OK );
    }

    @PutMapping("/v1/users/role")
    public ResponseEntity<APIMessage<Boolean>> updateRole(@RequestBody ReqRole reqRole){
        if(userService.updateRole(reqRole)){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "권한등록",true),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "권한등록",false),HttpStatus.OK);
    }
}
