package numble.server.timedeal.domain.user.util;


public class IdGenerator {
    public static String generateId(String email){
        return email.substring(0,email.indexOf("@")) + ((int)(Math.random()*9001) + 1000);
    }
}
