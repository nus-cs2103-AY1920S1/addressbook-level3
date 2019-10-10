package com.dukeacademy.solution.environment;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.CompilerFileCreationException;
import com.dukeacademy.solution.models.JavaFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * The standard environment class used by the compiler to perform file management.
 */
public class StandardCompilerEnvironment implements CompilerEnvironment {
    private String MESSAGE_NO_PERMISSIONS = "Not permitted to create compiler environment in the following location.";
    private String MESSAGE_JAVA_FILE_NOT_FOUND = "No such file found in the compiler environment.";
    private String MESSAGE_CREATE_ENVIRONMENT_FAILED = "Failed to create compiler environment.";
    private String MESSAGE_CREATE_JAVA_FILE_FAILED = "Failed to create java file";
    private String MESSAGE_WRITE_JAVA_FILE_FAILED = "Failed to write to java file";
    private String MESSAGE_CLEAR_ENVIRONMENT_FAILED = "Compiler environment not cleared, remnant files may persist";

    private Logger logger = LogsCenter.getLogger(StandardCompilerEnvironment.class);
    private Path locationPath;

    private List<JavaFile> createdFiles;

    public StandardCompilerEnvironment(String locationPath) throws CompilerEnvironmentException {
        this.locationPath = Path.of(locationPath);
        this.createdFiles = new ArrayList<>();

        boolean isCreateDirectorySuccessful;

        try {
            String directoryPath = this.locationPath.toUri().getPath();
            isCreateDirectorySuccessful = new File(directoryPath).mkdir();
        } catch (SecurityException e) {
            throw new CompilerEnvironmentException(MESSAGE_NO_PERMISSIONS, e);
        }

        if (!isCreateDirectorySuccessful) {
            throw new CompilerEnvironmentException(MESSAGE_CREATE_ENVIRONMENT_FAILED);
        }
    }

    public String getLocationPath() {
        return this.locationPath.toUri().getPath();
    }

    @Override
    public JavaFile createJavaFile(UserProgram program) throws CompilerFileCreationException {
        try {
            this.clearEnvironment();
        } catch (CompilerEnvironmentException e) {
            throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
        }

        String name = program.getClassName();
        String content = program.getSourceCodeAsString();

        String canonicalName = this.getCanonicalName(name, content);

        File file = this.createFile(canonicalName);

        boolean isFileCreationSuccessful;

        try {
            isFileCreationSuccessful = file.createNewFile();
        } catch (IOException e) {
            throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
        }

        if (!isFileCreationSuccessful) {
            throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Writer fileWriter = new OutputStreamWriter(fileOutputStream);

            fileWriter.write(content);

            fileWriter.close();
            JavaFile javaFile = new JavaFile(canonicalName, locationPath.toUri().getPath());
            this.createdFiles.add(javaFile);

            return javaFile;
        } catch (IOException e) {
            this.clearDirectoryAfterFileWriteFailed();
            throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
        }
    }

    private String getCanonicalName(String name, String content) {
        String packageStatement = content.split(";")[0];
        if (!packageStatement.startsWith("package")) {
            return name;
        }

        return packageStatement.replace("package", "").trim() + "." + name;
    }

    private File createFile(String canonicalName) throws CompilerFileCreationException {
        String[] nestedFiles = canonicalName.split("\\.");
        int numNestedFiles = nestedFiles.length;
        Path filePath = this.locationPath;

        if (numNestedFiles <= 1) {
            return filePath.resolve(canonicalName + ".java").toFile();
        }

        for (int i = 0; i < numNestedFiles - 1; i++) {
            filePath = filePath.resolve(nestedFiles[i]);

            if (!filePath.toFile().mkdir()) {
                throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
            }
        }

        return filePath.resolve(nestedFiles[numNestedFiles - 1] + ".java").toFile();
    }

    private void clearDirectoryAfterFileWriteFailed() throws CompilerFileCreationException {
        try {
            this.clearEnvironment();
        } catch (CompilerEnvironmentException e) {
            throw new CompilerFileCreationException(MESSAGE_WRITE_JAVA_FILE_FAILED + " " + MESSAGE_CLEAR_ENVIRONMENT_FAILED);
        }
    }

    @Override
    public JavaFile getJavaFile(String canonicalName) throws FileNotFoundException {
        Optional<JavaFile> file = this.createdFiles.stream()
                .filter(javaFile -> javaFile.getCanonicalName().equals(canonicalName))
                .findFirst();

        return file.orElseThrow(() -> new FileNotFoundException(MESSAGE_JAVA_FILE_NOT_FOUND));
    }

    @Override
    public void clearEnvironment() throws CompilerEnvironmentException {
        try {
            Files.walk(locationPath)
                    .filter(path -> !path.equals(locationPath))
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);

            this.createdFiles = new ArrayList<>();
        } catch (IOException e) {
            throw new CompilerEnvironmentException(MESSAGE_CLEAR_ENVIRONMENT_FAILED);
        }
    }

    @Override
    public void close() {
        try {
            Files.walk(locationPath)
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);
            this.createdFiles = new ArrayList<>();
        } catch (IOException e) {
            logger.fine(MESSAGE_CLEAR_ENVIRONMENT_FAILED);
        }
    }
}
