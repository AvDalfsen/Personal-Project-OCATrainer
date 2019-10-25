package nl.sogyo.ocatrainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Tests {
    private Map<String, Boolean> testResults = new HashMap<>();

    Map<String, Boolean> runTests(String results, String code, int exerciseId) throws IOException {
        switch(exerciseId){
            case 1:
                testResults.put("The code should compile!", results.contains("compiled successfully"));
                testResults.put("The code should print something.", code.contains("System.out.print"));
                testResults.put("The purpose of this exercise is to write code that will print 8.", results.contains("8"));
                return testResults;
            case 2:
                Reader inputString = new StringReader(code);
                BufferedReader reader = new BufferedReader(inputString);
                String line;
                String methodLine = null;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("newMethod")) {
                        methodLine = line;
                    }
                }
                testResults.put("The code should compile!", results.contains("compiled successfully"));
                testResults.put("The code should contain a method called 'newMethod'.", code.contains("newMethod("));
                try {
                    testResults.put("The new method should be accessible to everyone.", methodLine.contains("public"));
                    testResults.put("The new method should not return anything.", methodLine.contains("void"));
                    testResults.put("The new method should have a parameter", methodLine.contains("(String"));
                } catch (NullPointerException e) {
                    testResults.put("Some tests require the code to contain a method called 'newMethod' in order to be able run.", false);
                }
                return testResults;
        }
        return null;
    }
}
