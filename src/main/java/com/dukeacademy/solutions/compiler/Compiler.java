package com.dukeacademy.solutions.compiler;

import com.dukeacademy.solutions.exceptions.CompilerException;
import com.dukeacademy.solutions.exceptions.CompilerFileContentException;
import com.dukeacademy.solutions.models.ClassFile;
import com.dukeacademy.solutions.models.JavaFile;

/**
 * Represents a compiler used by the application to compile java files into class files.
 */
public interface Compiler {
    /**
     * Compiles the given java file into a class file.
     * @param file the java file to be compiled.
     * @return the compiled class file.
     * @throws CompilerException if the file fails to compile due to the compiler.
     * @throws CompilerFileContentException if the file fails to compile due to the contents.
     */
    public ClassFile compileJavaFile(JavaFile file) throws CompilerException, CompilerFileContentException;
}
