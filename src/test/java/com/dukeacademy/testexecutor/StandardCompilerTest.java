package com.dukeacademy.testexecutor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.testexecutor.compiler.StandardCompiler;
import com.dukeacademy.testexecutor.exceptions.CompilerException;
import com.dukeacademy.testexecutor.exceptions.CompilerFileContentException;
import com.dukeacademy.testexecutor.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.models.JavaFile;

class StandardCompilerTest {
    @TempDir
    public Path tempFolder;
    private Path environmentPath;

    private StandardCompiler standardCompiler;

    private UserProgram validProgram = new UserProgram("ValidTest", "public class ValidTest {\n"
            + "\tpublic static void main(String args[]) {"
            + "\t\tSystem.out.println(\"Hello world\");"
            + "\t}"
            + "\n}");

    private UserProgram validProgram1 = new UserProgram("ValidTest1", "public class ValidTest1 {\n"
            + "\tclass NestedClass {\n"
            + "\t\tprivate int x = 0;\n"
            + "\t}\n"
            + "\tpublic static void main(String args[]) {"
            + "\t\tSystem.out.println(\"Hello world\");"
            + "\t}"
            + "\n}");

    private UserProgram invalidProgram = new UserProgram("InvalidTest", "FooBar");

    @BeforeEach
    public void initializeTest() {
        standardCompiler = new StandardCompiler();
        environmentPath = tempFolder.resolve("compiler");
        environmentPath.toFile().mkdir();
    }

    @Test
    public void compileProgram() throws CompilerException, CompilerFileContentException,
            IOException, JavaFileCreationException {
        JavaFile validJavaFile = this.createJavaFile(validProgram);
        standardCompiler.compileJavaFile(validJavaFile);

        Path validJavaFilePath = environmentPath.resolve(validProgram.getClassName() + ".java");
        Path validClassFilePath = environmentPath.resolve(validProgram.getClassName() + ".class");

        assertTrue(validJavaFilePath.toFile().exists());
        assertTrue(validClassFilePath.toFile().exists());

        JavaFile validJavaFile1 = this.createJavaFile(validProgram1);
        standardCompiler.compileJavaFile(validJavaFile1);

        Path validJavaFilePath1 = environmentPath.resolve(validProgram1.getClassName() + ".java");
        Path validClassFilePath1 = environmentPath.resolve(validProgram1.getClassName() + ".class");
        Path validClassFilePath2 = environmentPath.resolve("ValidTest1$NestedClass.class");

        assertTrue(validJavaFilePath1.toFile().exists());
        assertTrue(validClassFilePath1.toFile().exists());
        assertTrue(validClassFilePath2.toFile().exists());

        JavaFile invalidJavaFile = this.createJavaFile(invalidProgram);
        assertThrows(CompilerFileContentException.class, () -> standardCompiler.compileJavaFile(invalidJavaFile));
        Path invalidClassFilePath = environmentPath.resolve(invalidProgram.getClassName() + ".class");

        assertFalse(invalidClassFilePath.toFile().exists());

    }

    /**
     * Creates a Java file for the purposes of the test.
     * @param program program to be written into the Java file.
     * @return a JavaFile instance.
     * @throws JavaFileCreationException if file creation fails.
     */
    private JavaFile createJavaFile(UserProgram program) throws IOException, JavaFileCreationException {
        String path = environmentPath.resolve(program.getClassName() + ".java").toUri().getPath();
        File file = new File(path);

        boolean isFileCreated = file.createNewFile();

        if (!isFileCreated) {
            throw new JavaFileCreationException("Unable to create file");
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Writer fileWriter = new OutputStreamWriter(fileOutputStream);

            fileWriter.write(program.getSourceCodeAsString());

            fileWriter.close();
        } catch (IOException e) {
            throw new JavaFileCreationException("Unable to create file.");
        }

        return new JavaFile(program.getClassName(), environmentPath.toUri().getPath());
    }
}
