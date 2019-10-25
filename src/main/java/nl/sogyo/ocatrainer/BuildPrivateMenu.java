package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import static java.lang.Integer.parseInt;

@StyleSheet("frontend://styles.css")

class BuildPrivateMenu extends VerticalLayout {
    BuildPrivateMenu() {
        Accordion privateMenuAccordion = new Accordion();
        VerticalLayout exercises = new VerticalLayout();
        int numberOfExercises = parseInt(new DatabaseRequests().queryDatabase("SELECT COUNT(*) FROM exercises"));
        for(int i = 1; i < (numberOfExercises + 1); i++) {
            Button button = new Button("Exercise " + i + " - " + new DatabaseRequests().queryDatabase("SELECT title FROM exercises WHERE idexercises = \"" + i + "\""));
            int finalI = i;
            button.addClickListener(clicked -> {
                new BuildPrivateContent().changeExercise(finalI);
            });
            button.setClassName("menu-button");
            exercises.add(button);
        }
        privateMenuAccordion.add("Exercises", exercises);

        add(privateMenuAccordion);
    }
}
