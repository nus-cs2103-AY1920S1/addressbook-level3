package com.dukeacademy.testexecutor.compiler;

import com.dukeacademy.testexecutor.compiler.exceptions.CompilerException;
import com.dukeacademy.testexecutor.compiler.exceptions.CompilerFileContentException;
import com.dukeacademy.testexecutor.models.ClassFile;
import com.dukeacademy.testexecutor.models.JavaFile;

/**
 * Represents a compiler used by the application to compile java files into class files. The compiled files should be
 * found in the same location of the original Java files.
 */
public interface Compiler {
    /**
     * Compiles the given java file into a Java Class file. Note that the Java file cannot be empty.
     * @param file the java file to be compiled
     * @return the compiled class file
     * @throws CompilerException if the file fails to compile due to compiler error
     * @throws CompilerFileContentException if the file fails to compile due to its contents
     */
    public ClassFile compileJavaFile(JavaFile file) throws CompilerException, CompilerFileContentException;
}
