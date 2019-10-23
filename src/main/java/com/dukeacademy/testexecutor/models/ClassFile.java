package com.dukeacademy.testexecutor.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

/**
 * Represents a java class file in the application. The canonical name refers to the name of the class you would
 * use in an import statement. This is needed to execute packaged classes from the root classpath.
 */
public class ClassFile {
    private String canonicalName;
    private String classPath;

    public ClassFile(String canonicalName, String classPath) throws FileNotFoundException {
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

    public String getAbsolutePath() {
        return Paths.get(classPath).resolve(canonicalName.replace(".", File.separator) + ".class").toString();
    }

    public File getFile() {
        String absolutePath = this.getAbsolutePath();
        return new File(absolutePath);
    }
}
