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
     *
     * @param canonicalName the canonical name to be checked
     * @param program       the program to be checked
     * @return true of they match
     */
    public static boolean checkCanonicalNameMatchesProgram(String canonicalName, String program) {
        boolean packageStatementMatch = checkPackageMatch(canonicalName, program);
        boolean containsMatchingClass = checkContainsMatchingClass(canonicalName, program);

        return packageStatementMatch && containsMatchingClass;
    }

    /**
     * Checks if a program's package statement matches the given canonical name.
     * @param canonicalName the canonical name to be checked
     * @param program the program to be checked
     * @return true if they match
     */
    private static boolean checkPackageMatch(String canonicalName, String program) {
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

        return true;
    }

    /**
     * Checks if the program contains an outer class that matches the one in the canonical name.
     * @param canonicalName the canonical name to be checked
     * @param program the program to be checked
     * @return true if a matching class is found
     */
    private static boolean checkContainsMatchingClass(String canonicalName, String program) {
        String[] canonicalNameSplit = canonicalName.split("\\.");
        String className = canonicalNameSplit[canonicalNameSplit.length - 1];

        // Remove any class declarations that could be in comments
        String programRemoveComments = program
                .replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)", "");

        // Split the program by class declaration with the required class name
        String[] programSplit = programRemoveComments.split("class " + className);

        if (programSplit.length == 1) {
            // Class declaration not found
            return false;
        }

        // For each occurrence of the class declaration, check to see if it is an outermost class declaration by
        // matching the number of opening/closing braces
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

            if (valid) {
                return true;
            }
        }

        return false;
    }
}
