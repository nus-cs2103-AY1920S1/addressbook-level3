package com.dukeacademy.testexecutor.models;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

/**
 * Represents a Class file in the user's file system. It contains the file's canonical name and classpath. Canonical
 * name refers to the name that is used in an import statement or when running a Java program from the command line.
 * E.g. com.dukeacademy.testexecutor.TestExecutor. A FileNotFoundException is thrown during instantiation if the Class
 * file does not actually exist.
 */
public class ClassFile {
    private String canonicalName;
    private String classPath;

    /**
     * Note that the class file corresponding to the canonical name and class path as specified in the arguments
     * must actually exist or a FileNotFoundException will be thrown. Both arguments cannot be null.
     * @param canonicalName the canonical name of the Class file
     * @param classPath the classpath of the Class file
     * @throws FileNotFoundException if the corresponding Class file does not exists
     */
    public ClassFile(String canonicalName, String classPath) throws FileNotFoundException {
        requireAllNonNull(canonicalName, classPath);

        this.canonicalName = canonicalName;
        this.classPath = classPath;

        if (!this.getFile().exists()) {
            throw new FileNotFoundException("No Class file found : " + this.toString());
        }
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    public String getClassPath() {
        return this.classPath;
    }

    /**
     * Combines and formats the class path and canonical name of the Class file to return its absolute path.
     * @return the absolute path at which the file is Class file is located
     */
    public String getAbsolutePath() {
        return Paths.get(classPath).resolve(canonicalName.replace(".", File.separator) + ".class").toString();
    }

    /**
     * Returns a Java standard File class instance that corresponds to the ClassFile instance.
     * @return a File instance corresponding to this ClassFile instance.
     */
    public File getFile() {
        String absolutePath = this.getAbsolutePath();
        return new File(absolutePath);
    }

    /**
     * Returns true the object is another ClassFile instance with the same canonical name and classpath.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof ClassFile) {
            ClassFile other = (ClassFile) object;
            return other.canonicalName.equals(this.canonicalName)
                    && other.classPath.equals(this.classPath);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getAbsolutePath();
    }
}
