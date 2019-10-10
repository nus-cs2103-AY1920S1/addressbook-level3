package com.dukeacademy.solution.models;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents a java class file in the application.
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

    public File getFile() {
        String absolutePath = classPath + canonicalName.replace(".", File.separator) + ".class";
        return new File(absolutePath);
    }
}
