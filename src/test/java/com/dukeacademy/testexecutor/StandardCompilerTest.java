package com.dukeacademy.testexecutor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.dukeacademy.testexecutor.compiler.exceptions.EmptyJavaFileException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.model.question.UserProgram;
import com.dukeacademy.testexecutor.compiler.StandardCompiler;
import com.dukeacademy.testexecutor.compiler.exceptions.CompilerException;
import com.dukeacademy.testexecutor.compiler.exceptions.CompilerFileContentException;
import com.dukeacademy.testexecutor.environment.exceptions.JavaFileCreationException;
import com.dukeacademy.testexecutor.models.JavaFile;

class StandardCompilerTest {
    @TempDir
    public Path tempFolder;

    private UserProgram validProgram = new UserProgram("ValidTest", "public class ValidTest {\n"
            + "\tpublic static void main(String args[]) {"
            + "\t\tSystem.out.println(\"Hello world\");"
            + "\t}"
            + "\n}");

    private UserProgram nestedProgram = new UserProgram("ValidTest1", "public class ValidTest1 {\n"
            + "\tclass NestedClass {\n"
            + "\t\tprivate int x = 0;\n"
            + "\t}\n"
            + "\tpublic static void main(String args[]) {"
            + "\t\tSystem.out.println(\"Hello world\");"
            + "\t}"
            + "\n}");

    private UserProgram nameMismatchProgram = new UserProgram("InvalidTest",
            "public class FooBar{}");

    private UserProgram invalidProgram = new UserProgram("InvalidTest1", "FooBar");

    private UserProgram compileErrorProgram = new UserProgram("InvalidTest2",
            "public class FooBar {\n\t"
                    + "int a  = \"I am a string!\"\n"
                    + "}");

    private UserProgram emptyProgram = new UserProgram("Empty", "");

    @Test
    public void testCompileProgram() throws CompilerException, CompilerFileContentException,
            IOException, JavaFileCreationException {
        Path testFolder = tempFolder.resolve("compile_test");
        testFolder.toFile().mkdirs();
        StandardCompiler compiler = new StandardCompiler();

        JavaFile validJavaFile = this.createJavaFile(validProgram, testFolder);
        compiler.compileJavaFile(validJavaFile);

        Path validJavaFilePath = testFolder.resolve(validProgram.getClassName() + ".java");
        Path validClassFilePath = testFolder.resolve(validProgram.getClassName() + ".class");

        assertTrue(validJavaFilePath.toFile().exists());
        assertTrue(validClassFilePath.toFile().exists());

        // Tests for nested classes
        JavaFile validJavaFile1 = this.createJavaFile(nestedProgram, testFolder);
        compiler.compileJavaFile(validJavaFile1);

        Path validJavaFilePath1 = testFolder.resolve(nestedProgram.getClassName() + ".java");
        Path validClassFilePath1 = testFolder.resolve(nestedProgram.getClassName() + ".class");
        // Nested classes should also be generated
        Path validClassFilePath2 = testFolder.resolve("ValidTest1$NestedClass.class");

        assertTrue(validJavaFilePath1.toFile().exists());
        assertTrue(validClassFilePath1.toFile().exists());
        assertTrue(validClassFilePath2.toFile().exists());
    }

    @Test
    public void testCompileProgram_invalid() throws IOException, JavaFileCreationException {
        Path testFolder = tempFolder.resolve("compileInvalid_test");
        testFolder.toFile().mkdirs();
        StandardCompiler compiler = new StandardCompiler();

        JavaFile nameMismatchFile = this.createJavaFile(nameMismatchProgram, testFolder);
        assertThrows(CompilerFileContentException.class, () -> compiler.compileJavaFile(nameMismatchFile));

        JavaFile invalidFile = this.createJavaFile(invalidProgram, testFolder);
        assertThrows(CompilerFileContentException.class, () -> compiler.compileJavaFile(invalidFile));

        JavaFile compileErrorFile = this.createJavaFile(compileErrorProgram, testFolder);
        assertThrows(CompilerFileContentException.class, () -> compiler.compileJavaFile(compileErrorFile));

        JavaFile emptyFile = this.createJavaFile(emptyProgram, testFolder);
        assertThrows(EmptyJavaFileException.class, () -> compiler.compileJavaFile(emptyFile));
    }

    /**
     * Creates a Java file within a folder for the test. Note that this method only works for non-packaged classes.
     */
    private JavaFile createJavaFile(UserProgram program, Path folder) throws IOException, JavaFileCreationException {
        Path filePath = folder.resolve(program.getClassName() + ".java");
        File javaFile = filePath.toFile();
        if (!javaFile.createNewFile()) {
            throw new JavaFileCreationException("Unable to create file");
        }

        Files.writeString(filePath, program.getSourceCode());

        return new JavaFile(program.getClassName(), folder.toString());
    }
}
