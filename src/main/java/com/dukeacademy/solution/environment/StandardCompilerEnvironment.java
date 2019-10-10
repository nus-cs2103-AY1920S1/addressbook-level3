package com.dukeacademy.solution.environment;

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

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.exceptions.CompilerEnvironmentException;
import com.dukeacademy.solution.exceptions.CompilerFileCreationException;
import com.dukeacademy.solution.models.JavaFile;

/**
 * The standard environment class used by the compiler to perform file management.
 */
public class StandardCompilerEnvironment implements CompilerEnvironment {
    private static String messageNoPermissions = "Not permitted to create compiler environment in "
            + "the following location";
    private static String messageJavaFileNotFound = "No such file found in the compiler environment";
    private static String messageCreateEnvironmentFailed = "Failed to create compiler environment";
    private static String messageCreateJavaFileFailed = "Failed to create java file";
    private static String messageWriteJavaFileFailed = "Failed to write to java file";
    private static String messageClearEnvironmentFailed = "Compiler environment not cleared, "
            + "remnant files may persist";

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
            throw new CompilerEnvironmentException(messageNoPermissions, e);
        }

        if (!isCreateDirectorySuccessful) {
            throw new CompilerEnvironmentException(messageCreateEnvironmentFailed);
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
            throw new CompilerFileCreationException(messageCreateJavaFileFailed);
        }

        String name = program.getClassName();
        String content = program.getSourceCodeAsString();

        String canonicalName = this.getCanonicalName(name, content);

        File file = this.createFile(canonicalName);

        boolean isFileCreationSuccessful;

        try {
            isFileCreationSuccessful = file.createNewFile();
        } catch (IOException e) {
            throw new CompilerFileCreationException(messageCreateJavaFileFailed);
        }

        if (!isFileCreationSuccessful) {
            throw new CompilerFileCreationException(messageCreateJavaFileFailed);
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
            throw new CompilerFileCreationException(messageCreateJavaFileFailed);
        }
    }

    private String getCanonicalName(String name, String content) {
        String packageStatement = content.split(";")[0];
        if (!packageStatement.startsWith("package")) {
            return name;
        }

        return packageStatement.replace("package", "").trim() + "." + name;
    }

    /**
     * Creates a java file in the environment from the file's canonical name.
     * @param canonicalName the canonical name of the file to be created.
     * @return the created file.
     * @throws CompilerFileCreationException if the file fails to be created.
     */
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
                throw new CompilerFileCreationException(messageCreateJavaFileFailed);
            }
        }

        return filePath.resolve(nestedFiles[numNestedFiles - 1] + ".java").toFile();
    }

    /**
     * Clears the directory of the empty java file if the contents fail to be written into it.
     * @throws CompilerFileCreationException if the directory fails to be cleared.
     */
    private void clearDirectoryAfterFileWriteFailed() throws CompilerFileCreationException {
        try {
            this.clearEnvironment();
        } catch (CompilerEnvironmentException e) {
            throw new CompilerFileCreationException(messageWriteJavaFileFailed + " "
                    + messageClearEnvironmentFailed);
        }
    }

    @Override
    public JavaFile getJavaFile(String canonicalName) throws FileNotFoundException {
        Optional<JavaFile> file = this.createdFiles.stream()
                .filter(javaFile -> javaFile.getCanonicalName().equals(canonicalName))
                .findFirst();

        return file.orElseThrow(() -> new FileNotFoundException(messageJavaFileNotFound));
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
            throw new CompilerEnvironmentException(messageClearEnvironmentFailed);
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
            logger.fine(messageClearEnvironmentFailed);
        }
    }
}
