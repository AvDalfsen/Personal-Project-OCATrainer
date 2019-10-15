package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = Lumo.class)
@StyleSheet("frontend://styles.css")
@PageTitle("OCA Trainer")
@Route
public class MainView extends VerticalLayout {
    public MainView() {
        addClassName("main-view");

        if (!new AuthenticationService().isAuthenticated()) {
            showPublicComponent();
        } else {
            showPrivateComponent();
        }
    }

    private void showPublicComponent() {
        removeAll();
        add(new BuildPublicUI());
    }

    private void showPrivateComponent() {
        removeAll();
        add(new BuildPrivateUI());
    }

}