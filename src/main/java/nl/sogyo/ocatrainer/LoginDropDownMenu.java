package nl.sogyo.ocatrainer;

import com.github.appreciated.papermenubutton.PaperMenuButton;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.sql.SQLException;

class LoginDropDownMenu extends HorizontalLayout {
    LoginDropDownMenu() {
        //Dropdown Layout
        VerticalLayout popupContent = new VerticalLayout();
        popupContent.getStyle().set("border", "1px solid var(--lumo-primary-color)");
        popupContent.getThemeList().add("dark");
        popupContent.setWidth("300px");
        popupContent.setHeight("400px");
        popupContent.setAlignItems(FlexComponent.Alignment.CENTER);
        popupContent.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        //Login layout
        TextField usernameField = new TextField("Username");
        usernameField.setPlaceholder("Username");
        usernameField.setMinLength(1);
        usernameField.setErrorMessage("Please enter a username.");
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("Password");
        passwordField.setMinLength(1);
        passwordField.setErrorMessage("Please enter a password.");
        Checkbox rememberMe = new Checkbox("Remember me");

        Button loginButton = new Button("Login");
        Button newUserButton = new Button("New User");

        //New User Layout
        TextField newUsernameField = new TextField("New username");
        newUsernameField.setPlaceholder("New username");
        newUsernameField.setMinLength(1);
        newUsernameField.setErrorMessage("Please enter a username.");
        PasswordField newPasswordField = new PasswordField("Password");
        newPasswordField.setPlaceholder("Password");
        newPasswordField.setMinLength(8);
        newPasswordField.setErrorMessage("Please enter a password of at least 8 characters.");
        newPasswordField.isPreventInvalidInput();
        EmailField newEmailField = new EmailField("Your E-Mail");
        newEmailField.setValue("example@replace.me");
        newEmailField.setErrorMessage("Please enter a valid email address.");
        newEmailField.isPreventInvalidInput();
        Button submitNewUserButton = new Button("Submit new user");
        Button cancelButton = new Button("Cancel");

        passwordField.setClassName("text-field");
        newPasswordField.setClassName("text-field");
        usernameField.setClassName("text-field");
        newUsernameField.setClassName("text-field");
        newEmailField.setClassName("text-field");
        rememberMe.setClassName("checkbox");

        //Buttons
        loginButton.addClickListener(buttonClickEvent -> {
            try { new AuthenticationService().login(usernameField.getValue(), passwordField.getValue(), rememberMe.getValue()); }
            catch (SQLException | ClassNotFoundException e) { e.printStackTrace(); }
        });

        newUserButton.addClickListener(buttonClickEvent -> {popupContent.removeAll();
                                                            popupContent.add(newUsernameField, newPasswordField, newEmailField, submitNewUserButton, cancelButton);
        });
        submitNewUserButton.addClickListener(buttonClickEvent -> {
            if(newPasswordField.getValue().length() >= newPasswordField.getMinLength()) {
                try {
                    new DatabaseRequests().updateDatabase(
                            "INSERT INTO users(username, password, email) VALUES (\"" +
                                    newUsernameField.getValue() + "\",\"" +
                                    new UserService().hashPassword(newPasswordField.getValue()) + "\",\"" +
                                    newEmailField.getValue() + "\")");
                    Notification.show("Hello and welcome, " + newUsernameField.getValue() + "!\nFeel free to log in.", 4000, Notification.Position.TOP_CENTER);
                    popupContent.removeAll();
                    popupContent.add(usernameField, passwordField, rememberMe, loginButton, newUserButton);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    Notification.show("That username and/or email is already taken.", 4000, Notification.Position.MIDDLE);
                }
            }
        });

        cancelButton.addClickListener(buttonClickEvent -> {
            popupContent.removeAll();
            popupContent.add(usernameField, passwordField, rememberMe, loginButton, newUserButton);
        });

        popupContent.add(usernameField, passwordField, rememberMe, loginButton, newUserButton);

        add(new PaperMenuButton(new Button("Login"), popupContent));
    }
}