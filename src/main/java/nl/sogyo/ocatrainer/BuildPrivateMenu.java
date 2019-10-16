package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import static java.lang.Integer.parseInt;

class BuildPrivateMenu extends VerticalLayout {
    BuildPrivateMenu() {
        Accordion privateMenuAccordion = new Accordion();
        VerticalLayout exercises = new VerticalLayout();
        int numberOfExercises = parseInt(new DatabaseRequests().queryDatabase("SELECT COUNT(*) FROM exercises"));
        for(int i = 0; i < numberOfExercises; i++) {
            Button button = new Button("Exercise " + (i+1));
            int finalI = i;
            button.addClickListener(clicked -> new BuildPrivateContent().changeExercise(finalI));
            exercises.add(button);
        }
        privateMenuAccordion.add("Exercises", exercises);
        add(privateMenuAccordion);
    }
}
