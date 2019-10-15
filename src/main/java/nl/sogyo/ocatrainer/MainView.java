package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import javax.servlet.http.Cookie;
import java.io.IOException;

@Theme(value = Lumo.class)
@StyleSheet("frontend://styles.css")
@PageTitle("OCA Trainer")
@Route
public class MainView extends VerticalLayout {
    public MainView() {
        addClassName("main-view");
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        buildPublicHeader();
        buildPrivateBody();
    }

    private void buildPublicHeader() {
        H1 header = new H1("OCA Trainer");
        header.getElement().getThemeList().add("dark");
        LoginDropDownMenu dropDownButton = new LoginDropDownMenu();

        HorizontalLayout headerLayout = new HorizontalLayout(dropDownButton);
        headerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        header.add(headerLayout);
        add(header);
    }

    private void buildPrivateBody() {
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

    private Cookie getCookieByName(String name) {
        // Fetch all cookies from the request
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

        // Iterate to find cookie by its name
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }
}