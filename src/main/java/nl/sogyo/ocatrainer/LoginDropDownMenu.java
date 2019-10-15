package nl.sogyo.ocatrainer;

import com.github.appreciated.papermenubutton.PaperMenuButton;
import com.vaadin.flow.component.UI;
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
        EmailField newEmailField = new EmailField("Your E-Mail");
        newEmailField.setValue("example@replace.me");
        newEmailField.setErrorMessage("Please enter a valid email address.");
        Button submitNewUserButton = new Button("Submit new user");
        Button cancelButton = new Button("Cancel");

        //Buttons
        loginButton.addClickListener(buttonClickEvent -> {
            try { new AuthenticationService().login(usernameField.getValue(), passwordField.getValue(), rememberMe.getValue()); }
            catch (SQLException | ClassNotFoundException e) { e.printStackTrace(); }
        });

        newUserButton.addClickListener(buttonClickEvent -> popupContent.removeAll());
        newUserButton.addClickListener(buttonClickEvent -> popupContent.add(newUsernameField, newPasswordField, newEmailField, submitNewUserButton, cancelButton));
        submitNewUserButton.addClickListener(buttonClickEvent -> {
            try {
                new DatabaseRequests().updateDatabase(
                        "INSERT INTO users(username, password, email) VALUES (\""+newUsernameField.getValue()+"\",\""+newPasswordField.getValue()+"\",\""+newEmailField.getValue()+"\")");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                Notification.show("That username and/or email is already taken.", 4000, Notification.Position.MIDDLE);
            }
        });
        cancelButton.addClickListener(buttonClickEvent -> popupContent.removeAll());
        cancelButton.addClickListener(buttonClickEvent -> popupContent.add(usernameField, passwordField, loginButton, newUserButton));

        popupContent.add(usernameField, passwordField, rememberMe, loginButton, newUserButton);

        add(new PaperMenuButton(new Button("Login"), popupContent));
    }
}