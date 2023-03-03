package numble.server.timedeal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.user.UserEntity;
import numble.server.timedeal.domain.user.UserRepository;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.dto.request.ReqSignin;
import numble.server.timedeal.dto.request.ReqSignup;
import numble.server.timedeal.dto.response.RespUser;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();
    public APIMessage<Boolean> signup(ReqSignup reqSignup) {
        UserEntity userEntity = userRepository.save(convertToUserEntity(reqSignup));
        if(userEntity == null) {
            return new APIMessage<>(HttpStatus.OK.toString(),"회원가입실패",false);
        }
        APIMessage<Boolean> apiMessage = new APIMessage<>();
        apiMessage.setData(true);
        apiMessage.setMessage("회원가입성공");
        apiMessage.setStatus(HttpStatus.OK.toString());
        return apiMessage;
    }

    public boolean signin(ReqSignin reqSignin) {
        UserEntity userEntity = userRepository.findByNickname(reqSignin.getNickname());
        if(reqSignin.getPassword().equals(userEntity.getPassword())){
            return true;
        }
        return false;
    }

    public String findUserIdByNickname(String nickname) {
        UserEntity userEntity = userRepository.findByNickname(nickname);
        if(userEntity == null){
            return null;
        }
        return userEntity.getNickname();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }


    public RespUser findById(String id) {
        UserEntity userEntity = userRepository.findById(id).get();
        RespUser respUser = convertToDto(userEntity);
        respUser.setCreatedAt(userEntity.getCreatedDate());
        return respUser;
    }

    private RespUser convertToDto(UserEntity userEntity){
        return modelMapper.map(userEntity, RespUser.class);
    }

    private UserEntity convertToUserEntity(ReqSignup reqSignup){
        return UserEntity.builder()
                .password(reqSignup.getPassword())
                .name(reqSignup.getName())
                .nickname(reqSignup.getNickname())
                .phone(reqSignup.getPhone())
                .email(reqSignup.getEmail())
                .build();
    }

    public Page<UserEntity> userPagination(Pageable pageable) {
        PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return userRepository.findAll(pr);
    }
}
