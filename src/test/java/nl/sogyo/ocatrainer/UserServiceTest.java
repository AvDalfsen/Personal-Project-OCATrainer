package nl.sogyo.ocatrainer;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserServiceTest {
    UserService userService = new UserService();

    @Test
    public void authenticUserTest() {
        Boolean expected = userService.isAuthenticUser("b", "b");
        assertTrue("Tests whether user 'b' with password 'b' is an authentic user (that user may get removed; keep in mind)", expected);
    }

    @Test
    public void insertUserWithRandomIdRememberUserAndRemoveRememberedUserTest() throws SQLException, ClassNotFoundException {
        String randomID = userService.rememberUser("usernamefortestpurposes");
        String user = userService.getRememberedUser(randomID);
        assertEquals("The username should be callable using the randomId", "usernamefortestpurposes", user);
        userService.removeRememberedUser(randomID);
    }
}
