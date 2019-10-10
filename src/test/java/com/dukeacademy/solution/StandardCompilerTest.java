package com.dukeacademy.solution;

import com.dukeacademy.solution.compiler.StandardCompiler;
import com.dukeacademy.solution.exceptions.CompilerException;
import com.dukeacademy.solution.exceptions.CompilerFileContentException;
import com.dukeacademy.model.solution.UserProgram;
import com.dukeacademy.solution.exceptions.CompilerFileCreationException;
import com.dukeacademy.solution.models.JavaFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class StandardCompilerTest {
    @TempDir
    Path tempFolder;
    private Path environmentPath;

    private StandardCompiler standardCompiler;

    private UserProgram validProgram = new UserProgram("ValidTest", "public class ValidTest {\n"
            + "\tpublic static void main(String args[]) {"
            + "\t\tSystem.out.println(\"Hello world\");"
            + "\t}"
            + "\n}");

    private UserProgram invalidProgram = new UserProgram("InvalidTest", "FooBar");

    @BeforeEach
    void initializeTest() {
        standardCompiler = new StandardCompiler();
        environmentPath = tempFolder.resolve("compiler");
        environmentPath.toFile().mkdir();
    }

    @Test
    void compileProgram() throws CompilerException, CompilerFileContentException, IOException, CompilerFileCreationException {
        JavaFile validJavaFile = this.createJavaFile(validProgram);
        standardCompiler.compileJavaFile(validJavaFile);

        Path validJavaFilePath = environmentPath.resolve(validProgram.getClassName() + ".java");
        Path validClassFilePath = environmentPath.resolve(validProgram.getClassName() + ".class");

        assertTrue(validJavaFilePath.toFile().exists());
        assertTrue(validClassFilePath.toFile().exists());

        JavaFile invalidJavaFile = this.createJavaFile(invalidProgram);
        assertThrows(CompilerFileContentException.class, () -> standardCompiler.compileJavaFile(invalidJavaFile));
        Path invalidClassFilePath = environmentPath.resolve(invalidProgram.getClassName() + ".class");

        assertFalse(invalidClassFilePath.toFile().exists());

    }

    private JavaFile createJavaFile(UserProgram program) throws IOException, CompilerFileCreationException {
        String path = environmentPath.resolve(program.getClassName() + ".java").toUri().getPath();
        File file = new File(path);

        boolean isFileCreated = file.createNewFile();

        if (!isFileCreated) {
            throw new CompilerFileCreationException("Unable to create file");
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        Writer fileWriter = new OutputStreamWriter(fileOutputStream);

        fileWriter.write(program.getSourceCodeAsString());

        fileWriter.close();

        return new JavaFile(program.getClassName(), environmentPath.toUri().getPath());
    }
}