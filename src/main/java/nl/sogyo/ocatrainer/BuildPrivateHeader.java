package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.sql.SQLException;

@StyleSheet("frontend://styles.css")

class BuildPrivateHeader extends VerticalLayout {
    BuildPrivateHeader() {
        addClassName("main-header");
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        H1 header = new H1("OCA Trainer");
        header.getElement().getThemeList().add("dark");

        Button signOutButton = new Button("Sign out");

        signOutButton.addClickListener(click -> {
            try {
                new AuthenticationService().logOut();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        HorizontalLayout headerLayout = new HorizontalLayout(signOutButton);
        headerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        header.add(headerLayout);
        add(header);
    }
}