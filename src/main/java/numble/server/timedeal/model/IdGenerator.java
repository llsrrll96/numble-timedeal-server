package numble.server.timedeal.model;


public class IdGenerator {
    public static String generateId(String email){
        return email.substring(0,email.indexOf("@")) + ((int)(Math.random()*8999) + 1000);
    }
}
