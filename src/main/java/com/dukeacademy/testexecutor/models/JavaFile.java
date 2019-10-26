package com.dukeacademy.testexecutor.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.testexecutor.TestExecutorUtils;
import com.dukeacademy.testexecutor.environment.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.exceptions.IncorrectCanonicalNameException;

/**
 * Represents a Java file in the application. The canonical name refers to the name of the class you would
 * use in an import statement e.g. DukeAcademy.model.program.JavaFile. This is needed to execute packaged classes
 * from the root classpath. Guarantees that the contents of the JavaFile matches the canonical name at creation.
 */
public class JavaFile {
    private static Logger logger = LogsCenter.getLogger(JavaFile.class);
    private String canonicalName;
    private String classPath;

    public JavaFile(String canonicalName, String classPath) throws FileNotFoundException,
            IncorrectCanonicalNameException, JavaFileCreationException {
        this.canonicalName = canonicalName;
        this.classPath = classPath;

        if (!this.getFile().exists()) {
            logger.warning("No Java file found : " + this);
            throw new FileNotFoundException("JavaFile creation failed due to FileNotFoundException : " + this);
        }

        if (!this.checkContentsValid()) {
            logger.warning("JavaFile creation failed due to incorrect canonical name : " + this);
            throw new IncorrectCanonicalNameException("Incorrect canonical name : " + this);
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
     * Checks if the canonical name of the JavaFile matches its contents. This is done by reading the contents
     * of the actual existing Java file.
     * @return true if the contents matches the canonical name
     * @throws JavaFileCreationException if the file cannot be read
     */
    private boolean checkContentsValid() throws JavaFileCreationException {
        try {
            String contents = Files.readString(Paths.get(this.getAbsolutePath()));

            return TestExecutorUtils.checkCanonicalNameMatchesProgram(canonicalName, contents);
        } catch (IOException e) {
            throw new JavaFileCreationException(e.getMessage(), e);
        }
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

    @Override
    public String toString() {
        return this.getAbsolutePath();
    }
}
