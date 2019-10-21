package nl.sogyo.ocatrainer;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;

class UserService {

    private SecureRandom random = new SecureRandom();

    boolean isAuthenticUser(String username, String password) {
        return username.equals(new DatabaseRequests().queryDatabase("SELECT username FROM users WHERE username = \"" + username + "\""))
                && hashPassword(password).equals(new DatabaseRequests().queryDatabase("SELECT password FROM users WHERE username = \"" + username + "\""));
    }

    String rememberUser(String username) throws SQLException, ClassNotFoundException {
        String randomId = new BigInteger(130, random).toString(32);
        new DatabaseRequests().updateDatabase("INSERT INTO remember_users(username, random_id) VALUES(\"" + username + "\", \"" + randomId + "\")");
        return randomId;
    }

    String getRememberedUser(String id) {
        return new DatabaseRequests().queryDatabase("SELECT username FROM remember_users WHERE random_id = \"" + id + "\"");
    }

    void removeRememberedUser(String id) throws SQLException, ClassNotFoundException {
        new DatabaseRequests().updateDatabase("DELETE FROM remember_users WHERE random_id = \"" + id + "\"");
    }

    String hashPassword(String password){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            password = "salt" + password;
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}