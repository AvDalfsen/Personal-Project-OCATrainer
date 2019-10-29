package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

class BuildPublicContent extends VerticalLayout {
    BuildPublicContent() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        Label text1 = new Label("This application can be used to tinker with practice code written specifically to demonstrate certain aspects of Java for the OCA exam.");
        Label text2 = new Label("You will have to register and login prior to getting started, though, as that will enable you to save your code and keep track of your progress.");
        Label text3 = new Label("Best of luck!");
        add(text1, text2, text3);
    }
}