package numble.server.timedeal.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserEnum {
    ROLE_USER,ROLE_ADMIN;

    @JsonCreator
    public static UserEnum from(String s){
        return UserEnum.valueOf(s.toUpperCase());
    }
}
