package nl.sogyo.ocatrainer;

class UserVerification {
    boolean usernameVerification(String username) {
        boolean matchingUsername;

        matchingUsername = new DatabaseRequests().queryDatabase("SELECT username FROM users WHERE username = \"" + username + "\"").equals(username);

        return matchingUsername;
    }

    boolean passwordVerification(String username, String password) {
        boolean matchingPassword;

        matchingPassword = new DatabaseRequests().queryDatabase("SELECT password FROM users WHERE username = \"" + username + "\"").equals(password);

        return matchingPassword;
    }
}
