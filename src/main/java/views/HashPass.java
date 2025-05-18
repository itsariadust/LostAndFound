package views;

import org.mindrot.jbcrypt.BCrypt;

public class HashPass {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("password", BCrypt.gensalt()));
    }
}
