package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.cookieconsent.CookieConsent;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@StyleSheet("frontend://styles.css")

class BuildPublicHeader extends VerticalLayout {
    BuildPublicHeader() {
        addClassName("main-header");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        H1 header = new H1("OCA Trainer");
        header.getElement().getThemeList().add("dark");
        LoginDropDownMenu dropDownButton = new LoginDropDownMenu();

        HorizontalLayout headerLayout = new HorizontalLayout(dropDownButton);
        headerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        header.add(headerLayout);
        header.setWidthFull();
        add(header);
        add(new Label("Welcome to the OCA Trainer!"));

        CookieConsent cookieNotification = new CookieConsent(
                "This website uses cookies to ensure you have the best user experience",
                "Got it", "Why?", "https://www.cookiesandyou.com/",
                CookieConsent.Position.BOTTOM);
        add(cookieNotification);
    }
}