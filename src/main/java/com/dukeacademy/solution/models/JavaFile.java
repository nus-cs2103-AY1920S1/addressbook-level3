package com.dukeacademy.solution.models;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents a Java file in the application. The canonical name refers to the name of the class you would
 * use in an import statement. This is needed to execute packaged classes from the root classpath.
 */
public class JavaFile {
    private String canonicalName;
    private String classPath;

    public JavaFile(String canonicalName, String classPath) throws FileNotFoundException {
        this.canonicalName = canonicalName;
        this.classPath = classPath;

        if (!this.getFile().exists()) {
            throw new FileNotFoundException();
        }
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    public String getClassPath() {
        return this.classPath;
    }

    public File getFile() {
        String trimmedClassPath = classPath;
        if (classPath.lastIndexOf(File.separator) == classPath.length() - 1) {
            trimmedClassPath = classPath.substring(0, classPath.length() - 1);
        }

        String absolutePath = trimmedClassPath + File.separator + canonicalName.replace(".", File.separator) + ".java";
        return new File(absolutePath);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof JavaFile) {
            return ((JavaFile) object).canonicalName.equals(this.canonicalName)
                    && ((JavaFile) object).classPath.equals(this.classPath);
        } else {
            return false;
        }
    }
}
