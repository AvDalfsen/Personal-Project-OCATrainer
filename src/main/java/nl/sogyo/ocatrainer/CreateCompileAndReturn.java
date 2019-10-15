package nl.sogyo.ocatrainer;

import javax.tools.*;
import java.io.*;
import java.util.Collections;

class CreateCompileAndReturn {
    String createCompileFile(String code) throws IOException {
        File file = new File("test.java");
        file.delete();
        File newFile = new File("test.java");

            //Add functionality to simply create a new file. Create a new iteration of the file. Remove all files but the ones created in the last minute or so.

        if (file.createNewFile()) System.out.println("File is created!");
        else System.out.println("File already exists.");

        FileWriter writer = new FileWriter(file);
        writer.write("import java.util.*;\n\n" + code);
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
            diagnosticResults = "Error found at line " + (diagnostic.getLineNumber() - 2) + "\n" + diagnostic.getCode() + " - "
                    + diagnostic.getKind() + " - " + diagnostic.getMessage(null);
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
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter++;
                compileResults.append(line);
                if(counter == 1000) return "Computer says: \"No.\"\n\n1000 lines of output counted. Assumed stack overflow.\nProgram aborted.";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Code compiled succesfully!\n\n" + compileResults.toString();
    }
}