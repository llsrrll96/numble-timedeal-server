package numble.server.timedeal.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.user.repository.UserEntity;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.domain.user.dto.ReqRole;
import numble.server.timedeal.domain.user.dto.ReqSignin;
import numble.server.timedeal.domain.user.dto.ReqSignup;
import numble.server.timedeal.domain.user.dto.RespUser;
import numble.server.timedeal.domain.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "users")
public class UserController {
    private final UserService userService;

    @PostMapping ("/v1/signup")
    public ResponseEntity<APIMessage<Boolean>> signup(@RequestBody ReqSignup reqSignup){
        return new ResponseEntity<>(userService.signup(reqSignup), HttpStatus.CREATED );
    }

    @PostMapping ("/v1/signin")
    public ResponseEntity<APIMessage<Boolean>> signin(HttpSession session, @RequestBody ReqSignin reqSignin){
        if(userService.signin(reqSignin)){
            session.setAttribute("id", userService.findUserIdByNickname(reqSignin.getNickname()));
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"로그인",true), HttpStatus.OK );
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(),"로그인",false), HttpStatus.BAD_REQUEST );
    }

    @GetMapping("/v1/logout")
    public ResponseEntity<APIMessage<Boolean>> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"로그아웃",true), HttpStatus.OK );
    }
    @DeleteMapping("/v1/{userid}")
    public ResponseEntity<APIMessage<Boolean>> deleteUser(@PathVariable String userid){
        userService.deleteUser(userid);
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"회원탈퇴",true), HttpStatus.OK );
    }

    @GetMapping("/v1/{userid}")
    public ResponseEntity<APIMessage<RespUser>> getUserInfo(@PathVariable String userid){
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(),"회원정보",userService.findUserById(userid)), HttpStatus.OK );
    }

    @GetMapping("/v1")
    public ResponseEntity<Page<UserEntity>> getUserPagination(@PageableDefault(size = 50)Pageable pageable){
        return new ResponseEntity<>(userService.getUserPagination(pageable), HttpStatus.OK );
    }

    @PutMapping("/v1/role")
    public ResponseEntity<APIMessage<Boolean>> updateRole(@RequestBody ReqRole reqRole){
        if(userService.updateRole(reqRole)){
            return new ResponseEntity<>(new APIMessage<>(HttpStatus.OK.toString(), "권한등록성공",true),HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIMessage<>(HttpStatus.BAD_REQUEST.toString(), "권한등록실패",false),HttpStatus.BAD_REQUEST);
    }
}
