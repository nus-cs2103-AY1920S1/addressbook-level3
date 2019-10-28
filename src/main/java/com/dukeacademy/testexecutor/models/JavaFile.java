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
    private static final Logger logger = LogsCenter.getLogger(JavaFile.class);
    private final String canonicalName;
    private final String classPath;

    /**
     * Instantiates a new Java file.
     *
     * @param canonicalName the canonical name
     * @param classPath     the class path
     * @throws FileNotFoundException           the file not found exception
     * @throws IncorrectCanonicalNameException the incorrect canonical name exception
     * @throws JavaFileCreationException       the java file creation exception
     */
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

    /**
     * Gets canonical name.
     *
     * @return the canonical name
     */
    public String getCanonicalName() {
        return this.canonicalName;
    }

    /**
     * Gets class path.
     *
     * @return the class path
     */
    public String getClassPath() {
        return this.classPath;
    }

    /**
     * Gets absolute path.
     *
     * @return the absolute path
     */
    public String getAbsolutePath() {
        return Paths.get(classPath).resolve(canonicalName.replace(".", File.separator) + ".java").toString();
    }

    /**
     * Gets file.
     *
     * @return the file
     */
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
