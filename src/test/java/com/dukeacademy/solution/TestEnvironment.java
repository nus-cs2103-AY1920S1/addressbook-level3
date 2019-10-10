package com.dukeacademy.solution;

import com.dukeacademy.solution.environment.CompilerEnvironment;
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

public class TestEnvironment implements CompilerEnvironment {
    Path tempPath;

    private String MESSAGE_CREATE_JAVA_FILE_FAILED = "Failed to create java file";

    private List<JavaFile> createdFiles;

    public TestEnvironment(Path tempPath) {
        this.tempPath = tempPath;
        this.tempPath.toFile().mkdir();

        this.createdFiles = new ArrayList<>();
    }

    @Override
    public JavaFile createJavaFile(String name, String content) throws CompilerFileCreationException, CompilerEnvironmentException {
        try {
            this.clearEnvironment();
        } catch (CompilerEnvironmentException e) {
            throw new CompilerFileCreationException(MESSAGE_CREATE_JAVA_FILE_FAILED);
        }

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
            JavaFile javaFile = new JavaFile(canonicalName, tempPath.toUri().getPath());
            this.createdFiles.add(javaFile);

            return javaFile;
        } catch (IOException e) {
            this.clearEnvironment();
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
        Path filePath = this.tempPath;

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
    @Override
    public JavaFile getJavaFile(String canonicalName) throws FileNotFoundException, CompilerEnvironmentException {
        Optional<JavaFile> file = this.createdFiles.stream()
                .filter(javaFile -> javaFile.getCanonicalName().equals(canonicalName))
                .findFirst();

        return file.orElseThrow(() -> new FileNotFoundException("Java file not found."));
    }

    @Override
    public void clearEnvironment() throws CompilerEnvironmentException {
        try {
            Files.walk(tempPath)
                    .filter(path -> !path.equals(tempPath))
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new CompilerEnvironmentException("Failed to clear folder.");
        }
    }

    @Override
    public void close() throws CompilerEnvironmentException {
        try {
            Files.walk(tempPath)
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new CompilerEnvironmentException("Failed to clear folder.");
        }
    }
}
