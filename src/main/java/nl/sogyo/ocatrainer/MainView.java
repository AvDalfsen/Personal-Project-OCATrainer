package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = Lumo.class)
@PageTitle("OCA Trainer")
@Route("")
public class MainView extends AppLayout {
    public MainView() {
        if (!new AuthenticationService().isAuthenticated()) {
            showPublicComponent();
        } else {
            showPrivateComponent();
        }
    }

    private void showPublicComponent() {
        addToNavbar(new BuildPublicHeader());
        addToDrawer(new BuildPublicMenu());
        setContent(new BuildPublicContent());
    }

    private void showPrivateComponent() {
        addToNavbar(new BuildPrivateHeader());
        addToDrawer(new BuildPrivateMenu());
        setContent(new BuildPrivateContent());
    }
}