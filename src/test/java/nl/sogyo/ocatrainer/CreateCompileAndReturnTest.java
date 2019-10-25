package nl.sogyo.ocatrainer;

import static org.junit.Assert.*;
import org.junit.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CreateCompileAndReturnTest {
    private CreateCompileAndReturn ccr = new CreateCompileAndReturn();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void fileCreationTest() throws IOException {
        ccr.createCompileFile("public class Henk{");
        File file = new File("./userfiles/Henk.java");

        assertTrue("When run, a new file should be created called test.java (this may change as the program develops, keep in mind).",file.exists());
    }

    @Test
    public void fileCreationContentTest() throws IOException {
        ccr.createCompileFile("public class Henk{");
        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get("./userfiles/Henk.java"), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append("\n"));

        assertTrue("When run with parameter 'Henk', the created file should contain the string 'Henk'",contentBuilder.toString().contains("Henk"));
    }

    @Test
    public void compileFailedResultsTest() throws IOException {
        String compileResults = ccr.createCompileFile("public class test{\n" +
                "\tpublic static void main(String[] args){\n" +
                "\t\tSystem.out.println(\"this is a test\")\n" +
                "\t}\n" +
                "}");
        assertTrue("If the code contains an error that causes it to fail to compile, it will return a message.", compileResults.contains("failed to compile"));
    }

    @Test
    public void runtimePassedResultsTest() throws IOException {
        String compileResults = ccr.createCompileFile("public class test{\n" +
                "\tpublic static void main(String[] args){\n" +
                "\t\tSystem.out.println(\"this is a test\");\n" +
                "\t}\n" +
                "}");
        assertTrue("If the file creation, compilation, and loading/running pass, it should return the results of the code.",compileResults.contains("this is a test"));
    }

    @Test
    public void runtimeStackOverflowTest() throws IOException {
        String compileResults = ccr.createCompileFile("public class test{\n" +
                "\tpublic static void main(String[] args){\n" +
                "\t\twhile(true) System.out.println(\"this is a test\");\n" +
                "\t}\n" +
                "}");
        assertTrue("If the submitted code prints 1000 lines it will be aborted. A message declaring this will be returned.",compileResults.contains("aborted"));
    }
}