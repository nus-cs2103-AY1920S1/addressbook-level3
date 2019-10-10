package com.dukeacademy.solution.models;

import java.io.File;
import java.io.FileNotFoundException;

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
}