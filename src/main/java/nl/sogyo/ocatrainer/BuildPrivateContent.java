package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.VaadinSession;

import java.io.IOException;
import java.sql.SQLException;

class BuildPrivateContent extends VerticalLayout {
    BuildPrivateContent() {
        H2 exerciseTitle = new H2("Welcome to the exercises! Feel free to pick one from the menu.");
        exerciseTitle.setId("exercise-title");

        TextArea codeField = new TextArea("Code your heart out.");
        codeField.setId("code-field");
        codeField.setMinWidth("42em");
        codeField.isAutofocus();
        codeField.isRequired();

        codeField.setValue("public class test{\n" +
                "\tpublic static void main(String[] args){\n" +
                "\t\tSystem.out.println(\"This is the starting code!\");\n" +
                "\t}\n" +
                "}");

        TextArea compileResults = new TextArea("Just look at 'dem results!");
        compileResults.setReadOnly(true);
        compileResults.setMinWidth("42em");

        Page page = UI.getCurrent().getPage();

        Button compileButton = new Button("Compile!");
        compileButton.addClickListener(click -> {
            try {
                compileResults.setValue(new CreateCompileAndReturn().createCompileFile(page.executeJs("document.getElementById(\"code-field\").value").toString()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("File not created!");
            }
        });

        String exerciseIdNumber = page.executeJs("document.getElementById(\"exercise-title\").value").toString();
        Button saveButton = new Button("Save code");
        saveButton.addClickListener(click -> {
//            try {
//                new DatabaseRequests().updateDatabase("INSERT INTO performance(username, idexercises, saved_code) VALUES(" +
//                        "\"" + VaadinSession.getCurrent().getAttribute("username").toString() + "\", " +
//                        "\"" + exerciseIdNumber.substring(exerciseIdNumber.length() - 1) + "\", " +
//                        "\"" + codeInCodeField + "\")");
                System.out.println(codeField.getValue());
//            } catch (ClassNotFoundException | SQLException e) {
//                e.printStackTrace();
//            }
        });

        add(
                exerciseTitle,
                new HorizontalLayout(
                        codeField,
                        compileResults
                ),
                compileButton,
                saveButton
        );
    }

    void changeExercise(int exerciseID) {
        String initial_code = new DatabaseRequests().queryDatabase("SELECT initial_code FROM exercises WHERE idexercises = \""+ (exerciseID + 1) + "\"");
        String user = VaadinSession.getCurrent().getAttribute("username").toString();
        String saved_code = new DatabaseRequests().queryDatabase("SELECT saved_code FROM performance " +
                "WHERE username = \"" + user + "\" AND idexercises = \"" + (exerciseID + 1) + "\"");

        Page page = UI.getCurrent().getPage();
        page.executeJs("document.getElementById(\"exercise-title\").value = \"Welcome to exercise " + (exerciseID + 1) + "\"");
        if(!saved_code.isEmpty()) page.executeJs("document.getElementById(\"code-field\").value = \"" + saved_code + "\"");
        else page.executeJs("document.getElementById(\"code-field\").value = \"" + initial_code + "\"");
    }
}