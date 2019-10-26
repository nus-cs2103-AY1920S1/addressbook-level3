package com.dukeacademy.testexecutor.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

/**
 * Represents a Java file in the application. The canonical name refers to the name of the class you would
 * use in an import statement e.g. DukeAcademy.model.program.JavaFile. This is needed to execute packaged classes
 * from the root classpath.
 */
public class JavaFile {
    private String canonicalName;
    private String classPath;

    public JavaFile(String canonicalName, String classPath) throws FileNotFoundException {
        this.canonicalName = canonicalName;
        this.classPath = classPath;

        if (!this.getFile().exists()) {
            throw new FileNotFoundException("No file " + canonicalName + " found at : " + classPath);
        }
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    public String getClassPath() {
        return this.classPath;
    }

    public String getAbsolutePath() {
        return Paths.get(classPath).resolve(canonicalName.replace(".", File.separator) + ".java").toString();
    }

    public File getFile() {
        return new File(this.getAbsolutePath());
    }

    /**
     * Returns true if the object is another instance of JavaFile with the same canonical name and classpath.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof JavaFile) {
            JavaFile other = (JavaFile) object;
            return other.canonicalName.equals(this.canonicalName)
                    && other.classPath.equals(this.classPath);
        } else {
            return false;
        }
    }
}
