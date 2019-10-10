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
import java.util.Comparator;

public class TestEnvironment implements CompilerEnvironment {
    Path tempPath;

    public TestEnvironment(Path tempPath) {
        this.tempPath = tempPath;
        this.tempPath.toFile().mkdir();
    }

    @Override
    public JavaFile createJavaFile(String name, String content) throws CompilerFileCreationException, CompilerEnvironmentException {
        try {
            this.clearEnvironment();
            File file = tempPath.resolve(name + ".java").toFile();

            if (file.createNewFile()) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                Writer fileWriter = new OutputStreamWriter(fileOutputStream);
                fileWriter.write(content);
                fileWriter.close();

                return new JavaFile("", "");
            }

            throw new CompilerFileCreationException("Failed to create file.");
        } catch (IOException e) {
            throw new CompilerFileCreationException("Failed to create file.");
        }
    }

    @Override
    public JavaFile getJavaFile(String name) throws FileNotFoundException, CompilerEnvironmentException {
        File file = tempPath.resolve(name + ".java").toFile();

        if (file.exists()) {
            return new JavaFile("", "");
        }

        throw new FileNotFoundException();
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
