package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.VaadinSession;
import dev.mett.vaadin.tooltip.Tooltips;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Integer.parseInt;

@StyleSheet("frontend://styles.css")

class BuildPrivateContent extends VerticalLayout {
    private String SESSION_EXERCISE = "exercise-id";

    private TextArea codeField = new TextArea("Code your heart out.");
    BuildPrivateContent() {
        addClassName("main-content");
        H2 exerciseTitle = new H2("Welcome to the exercises! Feel free to pick one from the menu.");
        exerciseTitle.setId("exercise-title");

        H5 exerciseDescription = new H5("Hello there, " + VaadinSession.getCurrent().getAttribute("username").toString() + "!");
        exerciseDescription.setId("exercise-description");

        codeField.setId("code-field");
        codeField.setMinWidth("39em");

        TextArea compileResults = new TextArea("Just look at 'dem results!");
        compileResults.setId("compile-results");
        compileResults.setReadOnly(true);
        compileResults.setMinWidth("39em");

        AtomicReference<String> code = new AtomicReference<>("");
        AtomicReference<String> results = new AtomicReference<>("");
        Button compileButton = new Button("Compile!");

        HorizontalLayout testLayout = new HorizontalLayout();
        testLayout.setWidth("80%");

        compileButton.addClickListener(click -> {
            try {
                testLayout.removeAll();
                results.set(new CreateCompileAndReturn().createCompileFile(codeField.getValue()));
                code.set(codeField.getValue());
                compileResults.setValue(String.valueOf(results));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    testLayout.add(testResults(String.valueOf(results), String.valueOf(code)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidth("80%");
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        buttonLayout.add(compileButton, saveButton, loadOriginalCode);

        add(
                exerciseTitle,
                exerciseDescription,
                new HorizontalLayout(
                        codeField,
                        compileResults
                ),
                testLayout,
                buttonLayout
        );
    }

    void changeExercise(int exerciseID) {
        remove();
        String initial_code = new DatabaseRequests().queryDatabase("SELECT initial_code FROM exercises WHERE idexercises = \"" + exerciseID + "\"");
        String user = VaadinSession.getCurrent().getAttribute("username").toString();
        String saved_code = new DatabaseRequests().queryDatabase("SELECT saved_code FROM performance " +
                "WHERE username = \'" + user + "\' AND idexercises = \'" + exerciseID + "\'");
        String title = new DatabaseRequests().queryDatabase("SELECT title FROM exercises WHERE idexercises = \"" + exerciseID + "\"");
        String description = new DatabaseRequests().queryDatabase("SELECT description FROM exercises WHERE idexercises = \"" + exerciseID + "\"");

        VaadinSession.getCurrent().setAttribute(SESSION_EXERCISE, exerciseID);

        Page page = UI.getCurrent().getPage();
        page.executeJs("document.getElementById(\"exercise-title\").firstChild.nodeValue = \"Welcome to exercise " + exerciseID + " - " + title + "\"");
        page.executeJs("document.getElementById(\"exercise-description\").firstChild.nodeValue = \"" + description + "\"");
        page.executeJs("document.getElementById(\"compile-results\").value = \'\'");
        if(!saved_code.isEmpty()) {
            page.executeJs("document.getElementById(\"code-field\").value = \'" + stringEscaper(saved_code) + "\'");
            codeField.setValue(saved_code);
        }
        else {
            page.executeJs("document.getElementById(\"code-field\").value = \'" + stringEscaper(initial_code) + "\'");
            codeField.setValue(initial_code);
        }
    }

    private String stringEscaper(String javaCode) {
        javaCode = javaCode.replace("\n", "\\n");
        javaCode = javaCode.replace("\t", "\\t");
        javaCode = javaCode.replace("\"", "\\\"");
        return javaCode;
    }

    private HorizontalLayout testResults(String results, String code) throws IOException {
        HorizontalLayout testResultDisplay = new HorizontalLayout();
        int exerciseNumber = parseInt(VaadinSession.getCurrent().getAttribute(SESSION_EXERCISE).toString());
        Map<String, Boolean> testResults = new Tests().runTests(results, code, exerciseNumber);
        int i = 1;
        for (Map.Entry<String, Boolean> entry : testResults.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Label printTestResult = new Label();
            if ((boolean) value) {
                printTestResult.setText("Test " + i + " passed!");
                printTestResult.setClassName("green");
                Tooltips.getCurrent().setTooltip(printTestResult,key);
                testResultDisplay.add(printTestResult);
            }
            else {
                printTestResult.setText("Test " + i + " failed!");
                printTestResult.setClassName("red");
                Tooltips.getCurrent().setTooltip(printTestResult,key);
                testResultDisplay.add(printTestResult);
            }
            i++;
        }
        testResultDisplay.setWidthFull();
        testResultDisplay.setJustifyContentMode(JustifyContentMode.EVENLY);
        return testResultDisplay;
    }
}