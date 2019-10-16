package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

import javax.servlet.http.Cookie;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

class AuthenticationService {
    private final String COOKIE_NAME = "remember-me";
    private final String SESSION_USERNAME = "username";
    boolean isAuthenticated() {
        return VaadinSession.getCurrent().getAttribute(SESSION_USERNAME) != null || loginRememberedUser();
    }

    public boolean login(String username, String password, boolean rememberMe) throws SQLException, ClassNotFoundException {
        if (new UserService().isAuthenticUser(username, password)) {
            VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, username);
            if (rememberMe) {
                rememberUser(username);
            }
            UI.getCurrent().getPage().executeJs("location.reload();");
            return true;
        }
        return false;
    }

    void logOut() throws SQLException, ClassNotFoundException {
        Optional<Cookie> cookie = getRememberMeCookie();
        if (cookie.isPresent()) {
            String id = cookie.get().getValue();
            new UserService().removeRememberedUser(id);
            deleteRememberMeCookie();
        }

        VaadinSession.getCurrent().close();
        UI.getCurrent().navigate("");
        UI.getCurrent().getPage().executeJs("location.reload();");
    }

    private Optional<Cookie> getRememberMeCookie() {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies).filter(c -> c.getName().equals(COOKIE_NAME)).findFirst();
        }

        return Optional.empty();
    }

    private boolean loginRememberedUser() {
        Optional<Cookie> rememberMeCookie = getRememberMeCookie();

        if (rememberMeCookie.isPresent()) {
            String id = rememberMeCookie.get().getValue();
            String username = new UserService().getRememberedUser(id);

            if (username != null) {
                VaadinSession.getCurrent().setAttribute(SESSION_USERNAME, username);
                return true;
            }
        }
        return false;
    }

    private void rememberUser(String username) throws SQLException, ClassNotFoundException {
        String id = new UserService().rememberUser(username);

        Cookie cookie = new Cookie(COOKIE_NAME, id);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    private void deleteRememberMeCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        VaadinService.getCurrentResponse().addCookie(cookie);
    }
}