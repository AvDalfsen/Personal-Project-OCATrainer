package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

class BuildPublicUI extends VerticalLayout {
    BuildPublicUI() {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        H1 header = new H1("OCA Trainer");
        header.getElement().getThemeList().add("dark");
        LoginDropDownMenu dropDownButton = new LoginDropDownMenu();

        HorizontalLayout headerLayout = new HorizontalLayout(dropDownButton);
        headerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        header.add(headerLayout);
        add(header);
        add("Welcome to the OCA Trainer! <br></br> Please log in to access the content of the website.");
    }
}