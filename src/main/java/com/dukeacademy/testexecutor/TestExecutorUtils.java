package com.dukeacademy.testexecutor;

import java.util.Stack;

/**
 * A utility class to handle complicated logic required in the TestExecutor package.
 */
public class TestExecutorUtils {
    /**
     * Checks if a given program matches the canonical name. I.e. it has the correct package and class declarations
     * to match the canonical name. E.g. dukeacademy.testexecutor.TestExecutorUtils should have the package statement
     * "package dukeacademy.testexecutor" and the class "TestExecutorUtils" declared as an outer class.
     * @param canonicalName
     * @param program
     * @return
     */
    public static boolean checkCanonicalNameMatchesProgram(String canonicalName, String program) {
        String[] canonicalNameSplit = canonicalName.split("\\.");
        String packageStatement = program.split(";")[0];

        if (packageStatement.startsWith("package")) {
            String[] packageStatementSplit = packageStatement.replaceFirst("package", "")
                    .trim().split("\\.");

            if (canonicalNameSplit.length != packageStatementSplit.length + 1) {
                return false;
            }

            for (int i = 0; i < packageStatementSplit.length; i++) {
                if (!canonicalNameSplit[i].equals(packageStatementSplit[i])) {
                    return false;
                }
            }
        }

        String className = canonicalNameSplit[canonicalNameSplit.length - 1];

        String[] programSplit = program.split("class " + className);

        if (programSplit.length == 1) {
            return false;
        }

        for (int i = 1; i < programSplit.length; i++) {
            Stack<Character> braceStack = new Stack<>();

            boolean valid = true;
            for (char c : programSplit[i].toCharArray()) {
                if (c == '{') {
                    braceStack.push('{');
                }

                if (c == '}') {
                    if (braceStack.size() == 0) {
                        valid = false;
                        break;
                    }
                    braceStack.pop();
                }
            }

            if (valid && braceStack.size() == 0) {
                return true;
            }
        }

        return false;
    }
}
