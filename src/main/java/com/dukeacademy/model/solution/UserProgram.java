package com.dukeacademy.model.solution;

/**
 * Represents a user's submission for a question.
 */
public class UserProgram {
    private String className;
    private String sourceCode;

    public UserProgram(String className, String sourceCode) {
        this.className = className;
        this.sourceCode = sourceCode;
    }

    public String getClassName() {
        return this.className;
    }

    public String getSourceCodeAsString() {
        return this.sourceCode;
    }

    /**
     * Returns the canonical name of the program which is the name of the class prepended by the package. For example,
     * "com.DukeAcademy.model.solution.UserProgram".
     * @return the canonical name of the user's program.
     */
    public String getCanonicalName() {
        // Check to see if the file has a package specified
        String packageStatement = this.sourceCode.split(";")[0];
        if (!packageStatement.startsWith("package")) {
            return className;
        }

        // Convert the package to an actual path
        return packageStatement.replace("package", "").trim() + "." + className;
    }
}
