package nl.sogyo.ocatrainer;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import javax.tools.*;
import java.io.*;
import java.util.Collections;

@Theme(value = Lumo.class)

@Route
public class MainView extends VerticalLayout {

    public MainView() {

        VerticalLayout codeTest = new VerticalLayout();
        TextArea codeField = new TextArea("Field for programming, yo.");
        codeField.setMinWidth("700px");
        codeField.setMinHeight("200px");
        codeField.isAutofocus();
        codeField.isRequired();
        codeField.setValue("public class test{\n" +
                "\tpublic static void main(String[] args){\n" +
                "\t\tSystem.out.println(\"this is a test\");\n" +
                "\t}\n" +
                "}\n");

        TextArea compileResults = new TextArea();
        compileResults.setMinWidth("700px");
        compileResults.setMinHeight("200px");

        Button addButton = new Button("Compile!");
        addButton.addClickListener(click -> {
            try {
                compileResults.setValue(createCompileFile(codeField.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("File not created!");
            }
        });
        add(
            new H1("This is a test for checking out code."),
            new HorizontalLayout(
                    codeField,
                    compileResults
            ),
            addButton,
            codeTest
        );
    }

    private String createCompileFile(String code) throws IOException {
        File file = new File("test.java");
        file.delete();
        File newFile = new File("test.java");
        if (file.createNewFile())
        {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }

        FileWriter writer = new FileWriter(file);
        writer.write("package nl.sogyo.ocatrainer;\n\n" + code);
        writer.close();
        return compileRequest(newFile);
    }

    private String compileRequest(File fileToCompile) throws IOException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        final StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, null, null);

        final File file = new File(fileToCompile.toURI());

        final Iterable< ? extends JavaFileObject > sources = manager.getJavaFileObjectsFromFiles(Collections.singletonList(file));
        final JavaCompiler.CompilationTask task = compiler.getTask( null, manager, diagnostics,null, null, sources );

        boolean success = task.call();
        String diagnosticResults = "";
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            diagnosticResults = diagnostic.getCode() + " - " + diagnostic.getKind() + " - " + diagnostic.getMessage(null);
        }
        manager.close();
        if(!success) return "Code failed to compile: " + diagnosticResults;
        return runFile();
    }

    private String runFile() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", "java test.java");

        StringBuilder compileResults = new StringBuilder();

        try {
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                compileResults.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    return "Code compiled succesfully!\n\n" + compileResults.toString();
    }
}