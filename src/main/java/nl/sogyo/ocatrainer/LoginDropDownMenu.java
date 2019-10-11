package nl.sogyo.ocatrainer;

import com.github.appreciated.papermenubutton.PaperMenuButton;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

class LoginDropDownMenu extends HorizontalLayout {
    LoginDropDownMenu() {
        VerticalLayout popupContent = new VerticalLayout();
        popupContent.getStyle().set("border", "1px solid var(--lumo-primary-color)");
        popupContent.getThemeList().add("dark");
        popupContent.setWidth("300px");
        popupContent.setHeight("300px");
        popupContent.setAlignItems(FlexComponent.Alignment.CENTER);
        popupContent.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        TextField usernameField = new TextField("Username");
        usernameField.setPlaceholder("Username");
        PasswordField passwordField = new PasswordField("Password");
        passwordField.setPlaceholder("Password");
        Button loginButton = new Button("Login");
        Button newUserButton = new Button("New User");

        TextField newUsernameField = new TextField("New username");
        newUsernameField.setPlaceholder("New username");
        PasswordField newPasswordField = new PasswordField("Password");
        newPasswordField.setPlaceholder("Password");
        Button submitNewUserButton = new Button("Submit new user");
        Button cancelButton = new Button("Cancel");

        loginButton.addClickListener(buttonClickEvent -> Notification.show(usernameField.getValue() + " " + passwordField.getValue()));
        newUserButton.addClickListener(buttonClickEvent -> popupContent.removeAll());
        newUserButton.addClickListener(buttonClickEvent -> popupContent.add(newUsernameField, newPasswordField, submitNewUserButton, cancelButton));
//        submitNewUserButton.addClickListener(buttonClickEvent -> new DatabaseRequests().createDatabaseConnection("INSERT INTO "));
        cancelButton.addClickListener(buttonClickEvent -> popupContent.removeAll());
        cancelButton.addClickListener(buttonClickEvent -> popupContent.add(usernameField, passwordField, loginButton, newUserButton));

        popupContent.add(usernameField, passwordField, loginButton, newUserButton);

        add(new PaperMenuButton(new Button("Login"), popupContent));
    }
}