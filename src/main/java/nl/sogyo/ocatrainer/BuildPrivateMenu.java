package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Label;
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
        privateMenuAccordion.add("Chapter 1", exercises);
        privateMenuAccordion.add("Chapter 2", new Label("More funky exercises here"));
        privateMenuAccordion.add("Chapter 3", new Label("More funky exercises here"));
        privateMenuAccordion.add("Chapter 4", new Label("More funky exercises here"));
        privateMenuAccordion.add("Chapter 5", new Label("More funky exercises here"));
        privateMenuAccordion.add("Chapter 6", new Label("More funky exercises here"));
        privateMenuAccordion.add("Chapter 7", new Label("More funky exercises here"));
        privateMenuAccordion.add("Chapter 8", new Label("More funky exercises here"));

        add(privateMenuAccordion);
    }
}