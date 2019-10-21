package nl.sogyo.ocatrainer;

import javax.tools.*;
import java.io.*;
import java.util.Collections;
import java.util.Date;

class CreateCompileAndReturn {
    private String fileName = "";
    String createCompileFile(String code) throws IOException {
        File dir = new File("./userfiles/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                long diff = new Date().getTime() - child.lastModified();
                if(diff > 1000 * 60 * 5) child.delete();
            }
        }

        // Changes print to println to avoid a potential stack overflow through e.g. while(true) System.out.print("a"); --> one long line
        if(code.contains("print(")) code = code.replace("print(", "println(");

        if(code.contains("import ")) return "Please remove any import statements; all necessary libraries have already been imported.";

        try {
            fileName = code.substring(code.lastIndexOf("public class ") + 13, code.indexOf("{"));
            fileName = fileName.trim();
        } catch (StringIndexOutOfBoundsException e){
            return "Your code needs to be contained in a public class.";
        }

        File file = new File("./userfiles/"+fileName+".java");
        file.delete();
        File newFile = new File("./userfiles/"+fileName+".java");

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
        processBuilder.command("cmd.exe", "/c", "java ./userfiles/" + fileName + ".java");

        StringBuilder compileResults = new StringBuilder();

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                counter++;
                compileResults.append(line);
                if(counter == 1000) return "Computer says: \"No.\"\n\nExcessive output. Assumed stack overflow.\nProgram aborted.";

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Code compiled succesfully!\n\n" + compileResults.toString();
    }
}