package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.VaadinSession;

import java.io.IOException;
import java.sql.SQLException;

class BuildPrivateContent extends VerticalLayout {
    private String SESSION_EXERCISE = "exercise-id";
    private TextArea codeField = new TextArea("Code your heart out.");
    BuildPrivateContent() {
        H2 exerciseTitle = new H2("Welcome to the exercises! Feel free to pick one from the menu.");
        exerciseTitle.setId("exercise-title");

        H5 exerciseDescription = new H5("Hello there, General Kenobi");
        exerciseDescription.setId("exercise-description");

        codeField.setId("code-field");
        codeField.setMinWidth("42em");

        TextArea compileResults = new TextArea("Just look at 'dem results!");
        compileResults.setId("compile-results");
        compileResults.setReadOnly(true);
        compileResults.setMinWidth("42em");

        Button compileButton = new Button("Compile!");
        compileButton.addClickListener(click -> {
            try {
                compileResults.setValue(new CreateCompileAndReturn().createCompileFile(codeField.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button saveButton = new Button("Save code");
        saveButton.addClickListener(click -> {
            try {
                new DatabaseRequests().updateDatabase("INSERT INTO performance(username, idexercises, saved_code) VALUES(\'"
                        + VaadinSession.getCurrent().getAttribute("username").toString() + "\', "
                        + VaadinSession.getCurrent().getAttribute(SESSION_EXERCISE)
                        + ", \'" + codeField.getValue() + "\')" +
                        "ON DUPLICATE KEY UPDATE saved_code = \'" + codeField.getValue() + "\';");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });

        Button loadOriginalCode = new Button("Load original code");
        loadOriginalCode.addClickListener(click -> {
            Page page = UI.getCurrent().getPage();
            int exerciseId = (int) VaadinSession.getCurrent().getAttribute(SESSION_EXERCISE);
            String initialCode = new DatabaseRequests().queryDatabase("SELECT initial_code FROM exercises WHERE idexercises = \'" + exerciseId + "\'");
            String javaScriptFuckeryCode = stringEscaper(initialCode);
            page.executeJs("document.getElementById(\"code-field\").value = \'" + javaScriptFuckeryCode + "\'");
        });

        add(
                exerciseTitle,
                exerciseDescription,
                new HorizontalLayout(
                        codeField,
                        compileResults
                ),
                compileButton,
                saveButton,
                loadOriginalCode
        );
    }

    void changeExercise(int exerciseID) {
        int correctedExerciseId = exerciseID + 1;
        String initial_code = new DatabaseRequests().queryDatabase("SELECT initial_code FROM exercises WHERE idexercises = \"" + correctedExerciseId + "\"");
        String user = VaadinSession.getCurrent().getAttribute("username").toString();
        String saved_code = new DatabaseRequests().queryDatabase("SELECT saved_code FROM performance " +
                "WHERE username = \'" + user + "\' AND idexercises = \'" + correctedExerciseId + "\'");
        String title = new DatabaseRequests().queryDatabase("SELECT title FROM exercises WHERE idexercises = \"" + correctedExerciseId + "\"");
        String description = new DatabaseRequests().queryDatabase("SELECT description FROM exercises WHERE idexercises = \"" + correctedExerciseId + "\"");

        VaadinSession.getCurrent().setAttribute(SESSION_EXERCISE, correctedExerciseId);

        Page page = UI.getCurrent().getPage();
        page.executeJs("document.getElementById(\"exercise-title\").firstChild.nodeValue = \"Welcome to exercise " + correctedExerciseId + " - " + title + "\"");
        page.executeJs("document.getElementById(\"exercise-description\").firstChild.nodeValue = \"" + description + "\"");
        page.executeJs("document.getElementById(\"compile-results\").value = \'\'");
        if(!saved_code.isEmpty()) {
            page.executeJs("document.getElementById(\"code-field\").value = \'" + stringEscaper(saved_code) + "\'");
            codeField.setValue(saved_code);
            Notification.show(codeField.getValue());
        }
        else {
            page.executeJs("document.getElementById(\"code-field\").value = \'" + stringEscaper(initial_code) + "\'");
            codeField.setValue(initial_code);
            Notification.show(codeField.getValue());
        }
    }

    private String stringEscaper(String javaCode) {
        javaCode = javaCode.replace("\n", "\\n");
        javaCode = javaCode.replace("\t", "\\t");
        javaCode = javaCode.replace("\"", "\\\"");
        return javaCode;
    }
}