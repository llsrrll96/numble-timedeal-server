package numble.server.timedeal.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.server.timedeal.domain.user.repository.UserEntity;
import numble.server.timedeal.domain.user.repository.UserRepository;
import numble.server.timedeal.dto.APIMessage;
import numble.server.timedeal.domain.user.dto.ReqRole;
import numble.server.timedeal.domain.user.dto.ReqSignin;
import numble.server.timedeal.domain.user.dto.ReqSignup;
import numble.server.timedeal.domain.user.dto.RespUser;
import numble.server.timedeal.domain.user.util.UserEnum;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public APIMessage<Boolean> signup(ReqSignup reqSignup) {
        UserEntity user = convertToUserEntity(reqSignup);
        user.setRole(UserEnum.ROLE_USER);
        userRepository.save(user);

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
        return userEntity.getUserId();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public RespUser findByIdAndConvertDto(String id) {
        UserEntity userEntity = userRepository.findById(id).get();
        RespUser respUser = convertToDto(userEntity);
        respUser.setCreatedAt(userEntity.getCreatedDate());
        return respUser;
    }

    public Page<UserEntity> userPagination(Pageable pageable) {
        PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return userRepository.findAll(pr);
    }

    @Transactional
    public boolean updateRole(ReqRole reqRole) {
        try{
            UserEntity userEntity =  userRepository.findById(reqRole.getUserid()).get();
            userEntity.setRole(UserEnum.from(reqRole.getRole()));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public UserEntity findById(String userid) {
        return userRepository.findById(userid).get();
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

    public boolean checkUserRole(String userId, UserEnum role){
        UserEntity user = userRepository.findByUserId(userId);
        if(user.getRole().equals(role)){
            return true;
        }
        return false;
    }
}
