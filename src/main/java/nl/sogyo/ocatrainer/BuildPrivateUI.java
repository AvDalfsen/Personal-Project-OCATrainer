package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

import java.io.IOException;
import java.sql.SQLException;

class BuildPrivateUI extends VerticalLayout {
    BuildPrivateUI() {
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        H1 header = new H1("OCA Trainer");
        header.getElement().getThemeList().add("dark");

        Button signOutButton = new Button("Sign out");

        signOutButton.addClickListener(click -> {
            try {
                new AuthenticationService().logOut();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        HorizontalLayout headerLayout = new HorizontalLayout(signOutButton);
        headerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        header.add(headerLayout);
        add(header);

        TextArea codeField = new TextArea("Code your heart out.");
        codeField.setMinWidth("700px");
        codeField.setMinHeight("200px");
        codeField.isAutofocus();
        codeField.isRequired();
        codeField.setValue("public class test{\n" +
                "\tpublic static void main(String[] args){\n" +
                "\t\tSystem.out.println(\"this is a test\");\n" +
                "\t}\n" +
                "}");

        TextArea compileResults = new TextArea("Just look at 'dem results!");
        compileResults.setReadOnly(true);
        compileResults.setMinWidth("700px");
        compileResults.setMinHeight("200px");

        Button compileButton = new Button("Compile!");

        compileButton.addClickListener(click -> {
            try {
                compileResults.setValue(new CreateCompileAndReturn().createCompileFile(codeField.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("File not created!");
            }
        });

        add(
                new H1("Compile that shiz, yo!"),
                new HorizontalLayout(
                        codeField,
                        compileResults
                ),
                compileButton
        );
    }
}
